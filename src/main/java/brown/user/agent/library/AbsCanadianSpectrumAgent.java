package brown.user.agent.library;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.spectrumauctions.sats.core.bidlang.xor.XORBid;
import org.spectrumauctions.sats.core.bidlang.xor.XORValue;
import org.spectrumauctions.sats.core.model.Bidder;
import org.spectrumauctions.sats.core.model.Bundle;
import org.spectrumauctions.sats.core.model.Good;
import org.spectrumauctions.sats.core.model.gsvm.GSVMBidder;
import org.spectrumauctions.sats.core.model.gsvm.GSVMLicense;

import brown.auction.value.valuation.IGeneralValuation;
import brown.communication.bid.IBidBundle;
import brown.communication.bid.library.OneSidedBidBundle;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.communication.messages.library.SATSTradeMessage;
import brown.communication.messages.library.SATSTradeRequestMessage;
import brown.communication.messages.library.TradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.DemandSet;
import brown.platform.item.library.Item;
import brown.platform.item.library.GSVM18Item;
import brown.user.agent.IAgent;

public abstract class AbsCanadianSpectrumAgent extends AbsAgent implements IAgent {
	private IGeneralValuation valuation;
	private Integer seed;
	private Integer index;
	private Integer position;
	private GSVMBidder bidder;
	private Integer auctionID;
	private Map<Long, GSVMLicense> allGoods;

	public AbsCanadianSpectrumAgent(String name) {
		super(name);
		this.valuation = null;
		this.bidder = null;
		this.allGoods = null;
		this.auctionID = null;
		this.seed = null;
		this.index = null;
		this.position = null;
	}

	@Override
	public void onInformationMessage(IInformationMessage informationMessage) {
		// noop
	}

	@Override
	public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) {
		System.out.println("ON TRADE REQUEST MESSAGE");
		this.auctionID = tradeRequestMessage.getAuctionID();
		System.out.println("HERE LIES BINTO\t" + tradeRequestMessage.getState());
		Map<String, Double> prices = tradeRequestMessage.getState().getReserves();
		System.out.println(tradeRequestMessage.getState().getTradeHistory());
		System.out.println("ALLOC:\t" + tradeRequestMessage.getState().getAllocation());
		System.out.println("RESERVES:\t" + prices);
		Map<GSVMLicense, Double> priceMap = new HashMap<>();
		for (Map.Entry<String, Double> ent : prices.entrySet()) {
			if (ent.getKey().equals("seed") || ent.getKey().equals("index") || ent.getKey().equals("position")) {
				continue;
			}
			priceMap.put(this.allGoods.get(Long.parseLong(ent.getKey())), ent.getValue());
		}
		DemandSet demand = this.getDemandSet(priceMap);
		if (demand == null) {
			demand = new DemandSet();
		}
		ICart cart = new Cart();
		double price = 0;
		for (GSVMLicense license : demand.items()) {
			cart.addToCart(new GSVM18Item(license.getId(), this.seed));
			price += prices.get(Long.toString(license.getId()));
		}
		Map<ICart, Double> bundle = new HashMap<>();
		if (price > 0) {
			System.out.println("My bid:\t" + cart + "\t" + price);
		}
		bundle.put(cart, price);
		IBidBundle bid = new OneSidedBidBundle(bundle);
		this.agentBackend.sendMessage(new TradeMessage(0, this.agentBackend.getPrivateID(), this.auctionID, bid));
	}
	
	protected abstract void onAuctionStart();
	protected abstract DemandSet getDemandSet(Map<GSVMLicense, Double> prices);

	@Override
	public void onValuationMessage(IValuationMessage valuationMessage) {
		System.out.println("ON VALUATION MESSAGE");
		this.valuation = valuationMessage.getValuation();
		
		// get seed
		ICart cart = new Cart();
		cart.addToCart(new Item("seed"));
		this.seed = new Double(this.valuation.getValuation(cart)).intValue();
		
		// get index
		cart = new Cart();
		cart.addToCart(new Item("index"));
		this.index = new Double(this.valuation.getValuation(cart)).intValue();
		
		// get position
		cart = new Cart();
		cart.addToCart(new Item("position"));
		this.position = new Double(this.valuation.getValuation(cart)).intValue();
		
		System.out.println("SEED: " + seed + " INDEX: " + index + " POSITION: " + position);
		
		// create GSVM SATS objects
		this.bidder = SATSUtil.createGSVM18Bidder(this.seed, this.index);
		this.allGoods = SATSUtil.mapIDToGSVM18License(this.bidder.getWorld());
		System.out.println(this.allGoods);
		
		System.out.println("BASE VALUES: " + this.bidder.getBaseValues());
		
		this.onAuctionStart();
	}

	@Override
	public void onSimulationReportMessage(ISimulationReportMessage simReportMessage) {
		System.out.println("ON SIMULATION REPORT");
	}
	
	protected double getValuation(DemandSet demandSet) {
		return this.bidder.calculateValue(demandSet.toBundle()).doubleValue();
	}
	
	protected double getValuation(Collection<GSVMLicense> goods) {
		return this.bidder.calculateValue(new Bundle<>(goods)).doubleValue();
	}
	
	protected double getValuation(GSVMLicense... goods) {
		return this.bidder.calculateValue(new Bundle<>(goods)).doubleValue();
	}

}
