package brown.user.agent.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.spectrumauctions.sats.core.bidlang.xor.XORBid;
import org.spectrumauctions.sats.core.bidlang.xor.XORValue;
import org.spectrumauctions.sats.core.model.Bidder;
import org.spectrumauctions.sats.core.model.Bundle;
import org.spectrumauctions.sats.core.model.Good;

import brown.auction.value.valuation.IGeneralValuation;
import brown.communication.bid.IBidBundle;
import brown.communication.bid.library.OneSidedBidBundle;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.communication.messages.library.SATSTradeMessage;
import brown.communication.messages.library.SATSTradeRequestMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.DemandSet;
import brown.platform.item.library.Item;
import brown.platform.item.library.SATSItem;
import brown.user.agent.IAgent;

public abstract class AbsCanadianSpectrumAgent extends AbsAgent implements IAgent {
	protected IGeneralValuation valuation;
	protected Bidder bidder;
	private Integer auctionID;

	public AbsCanadianSpectrumAgent(String name) {
		super(name);
		this.valuation = null;
		this.bidder = null;
		this.auctionID = null;
	}

	@Override
	public void onInformationMessage(IInformationMessage informationMessage) {
		// noop
	}

	@Override
	public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) {
		SATSTradeRequestMessage message = (SATSTradeRequestMessage)tradeRequestMessage;
		this.auctionID = message.getAuctionID();
		this.bidder = message.getSATSBidder();
		Map<String, Double> prices = message.getState().getReserves();
		boolean start = true;
		for (Double x : prices.values()) {
			if (x > 0) {
				start = false;
				break;
			}
		}
		if (start) {
			this.onAuctionStart();
		}
		DemandSet demand = this.getDemandSet(prices);
		if (demand == null) {
			return;
		}
		Map<ICart, Double> bidPrices = new HashMap<>();
		ICart cart = new Cart();
		List<XORValue<Good>> goods = new LinkedList<>();
		Bundle<Good> bundle = new Bundle<>();
		double val = 0;
		for (Good g : this.bidder.getWorld().getLicenses()) {
			if (demand.contains(Long.toString(g.getId()))) {
				bundle.add(g);
				val += prices.get(Long.toString(g.getId()));
				cart.addToCart(new SATSItem(g));
			}
		}
		goods.add(new XORValue<Good>(bundle, val));
		XORBid<Good> bid = new XORBid.Builder<Good>(this.bidder, goods).build();
		bidPrices.put(cart, val);
		IBidBundle tpBundle = new OneSidedBidBundle(bidPrices);
		this.agentBackend.sendMessage(new SATSTradeMessage(0, this.agentBackend.getPrivateID(), this.auctionID, tpBundle, bid));
	}
	
	protected abstract void onAuctionStart();
	protected abstract DemandSet getDemandSet(Map<String, Double> prices);

	@Override
	public void onValuationMessage(IValuationMessage valuationMessage) {
		this.valuation = valuationMessage.getValuation();
	}

	@Override
	public void onSimulationReportMessage(ISimulationReportMessage simReportMessage) {
		
	}
	
	protected double getValuation(DemandSet demandSet) {
		ICart cart = new Cart();
		for (Good g : this.bidder.getWorld().getLicenses()) {
			if (demandSet.contains(Long.toString(g.getId()))) {
				cart.addToCart(new SATSItem(g));
			}
		}
		return this.valuation.getValuation(cart);
	}

}
