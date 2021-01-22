package brown.user.agent.library;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.value.valuation.IGeneralValuation;
import brown.communication.bid.IBidBundle;
import brown.communication.bid.library.GVSM9BidBundle;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.communication.messages.library.TradeMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;
import brown.user.agent.library.localbid.IBidVector;
import brown.user.agent.library.localbid.ILinearPrices;
import brown.user.agent.library.localbid.LinearPrices;

public abstract class AbsGVSM9Agent extends AbsOnlineAgent implements IAgent {
	private int agentType;
	private IGeneralValuation valuation;
	private Integer auctionID;
	private int simulationCount;
	

	public AbsGVSM9Agent(String host, int port, ISetup gameSetup, String name) {
		super(host, port, gameSetup, name);
		this.valuation = null;
		this.agentType = 0;
		this.auctionID = null;
		this.simulationCount = 0;
	}
	
	
	public abstract IBidVector regionalBid(Set<IItem> items);
	public abstract IBidVector nationalBid(Set<IItem> items);
	public abstract void update(IMarketPublicState state, int simulationCount);
	public abstract void saveInfo();
	public abstract void loadInfo();
	
	private IBidVector bid(Set<IItem> items) {
		return (this.agentType == 0) ? nationalBid(items) : regionalBid(items);
	}

	@Override
	public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) {
		this.loadInfo();
		
		Integer auctionID = tradeRequestMessage.getAuctionID();
		this.auctionID = auctionID;
		Set<IItem> eligibleItems = new HashSet<>(tradeRequestMessage.getItems().getItems());
		for (IItem item : tradeRequestMessage.getItems().getItems()) {
			ICart c = new Cart();
			c.addToCart(item);
			if (this.valuation.getValuation(c) < 0) {
				eligibleItems.remove(item);
			}
		}
		
		IBidBundle bid = new GVSM9BidBundle(this.bid(eligibleItems), this.agentType);
		ITradeMessage actionMessage = new TradeMessage(-1, this.ID, auctionID, bid);
		this.CLIENT.sendTCP(actionMessage);
	}
	
	@Override
	public void onValuationMessage(IValuationMessage valuationMessage) {
		this.valuation = valuationMessage.getValuation();
		ICart c = new Cart();
		c.addToCart(new Item("agentType"));
		this.agentType = this.valuation.getValuation(c).intValue();
	}
	
	protected IGeneralValuation valuation() {
		return this.valuation;
	}
	
	@Override
	public void onSimulationReportMessage(ISimulationReportMessage simulationMessage) {
		if (simulationMessage.getMarketResults() == null || !simulationMessage.getMarketResults().containsKey(this.auctionID)) {
			return;
		}
		
		this.simulationCount++;
		
		this.update(simulationMessage.getMarketResults().get(this.auctionID), this.simulationCount);

		if (this.agentType == 0) {
			this.saveInfo();
		}
	}
	
	@Override
	public void onInformationMessage(IInformationMessage informationMessage) {
		// noop
	}
}
