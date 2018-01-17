package brown.messages.library;

import brown.agent.AbsAgent;
import brown.channels.server.TwoSidedAuction;

/**
 * Orders that do not fully clear during a given round may
 * carry over to the next round, or they may be cancelled. 
 * @author andrew
 */
public class MarketOrderMessage extends AbsMessage {
  
	public final Integer marketID;
	public final double buyShares;
	public final double sellShares;
	public final double price;
	public final boolean cancel;

	public MarketOrderMessage() {
		super(null);
		this.marketID = null;
		this.buyShares = 0;
		this.sellShares = 0;
		this.price = 0;
		this.cancel = false;
	}
	
	public MarketOrderMessage(Integer ID, TwoSidedAuction market, double buyShares, double sellShares, double price) {
		super(ID);
		this.marketID = market.getID();
		this.buyShares = buyShares;
		this.sellShares = sellShares;
		this.price = price;
		this.cancel = false;
	}
	
	public MarketOrderMessage(Integer ID, Integer marketID, double buyShares, double sellShares, double price) {
		super(ID);
		this.marketID = marketID;
		this.buyShares = buyShares;
		this.sellShares = sellShares;
		this.price = price;
		this.cancel = false;
	}
	
	public MarketOrderMessage(Integer ID, Integer marketID, double buyShares, 
			double sellShares, double price, boolean cancel) {
		super(ID);
		this.marketID = marketID;
		this.buyShares = buyShares;
		this.sellShares = sellShares;
		this.price = price;
		this.cancel = cancel;
	}

	@Override
	public void dispatch(AbsAgent agent) {
		// Noop
	}

}
