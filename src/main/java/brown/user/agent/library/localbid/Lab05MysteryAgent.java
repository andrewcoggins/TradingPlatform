package brown.user.agent.library.localbid; // TODO: change to the correct package name

import brown.auction.value.valuation.IGeneralValuation;
import brown.communication.bid.IBidBundle;
import brown.communication.bid.library.PerItemBidBundle;
import brown.communication.messages.*;
import brown.communication.messages.library.TradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;
import brown.user.agent.library.AbsOnlineAgent;
import brown.user.agent.library.localbid.IBidVector;

import java.util.*;

public class Lab05MysteryAgent extends AbsOnlineAgent implements IAgent {
	private IGeneralValuation valuation;
	private Integer auctionID;

	@SuppressWarnings("unused")
	public Lab05MysteryAgent(String host, int port, ISetup gameSetup, String name) {
		super(host, port, gameSetup, name);
		this.auctionID = null;
		this.valuation = null;
	}
	
	private IBidVector bid(Set<IItem> G) {
		IBidVector mv = new BidVector();
		for (IItem good : G) {
			ICart c = new Cart();
			c.addToCart(good);
			mv.setBid(good, Math.max(0, this.valuation.getValuation(c)));
		}
		return mv;
	}
	

	@Override
	public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) {
		Integer auctionID = tradeRequestMessage.getAuctionID();
		this.auctionID = auctionID;
		
		IBidBundle bid = new PerItemBidBundle(this.bid(new HashSet<>(tradeRequestMessage.getItems().getItems())));
		ITradeMessage actionMessage = new TradeMessage(-1, this.ID, this.auctionID, bid);
		this.CLIENT.sendTCP(actionMessage);
	}

	@Override
	public void onInformationMessage(IInformationMessage informationMessage) {
		// noop
	}


	@Override
	public void onValuationMessage(IValuationMessage valuationMessage) {
		this.valuation = valuationMessage.getValuation();
	}

	@Override
	public void onSimulationReportMessage(ISimulationReportMessage simulationMessage) {
		// noop
	}
}
