package brown.communication.messages.library;

import org.spectrumauctions.sats.core.model.Bidder;

import brown.platform.item.ICart;

public class SATSTradeRequestMessage extends TradeRequestMessage {
	private Bidder bidder;
	
	public SATSTradeRequestMessage() {
	    super();
	    this.bidder = null;
	  }
	  
	  // TODO: add a price? 
	  public SATSTradeRequestMessage(Integer messageID, Integer auctionID, Integer agentID, ICart items, Bidder bidder) {
	    super(messageID, auctionID, agentID, items);
	    this.bidder = bidder;
	  }
	  
	  public Bidder getSATSBidder() {
		  return this.bidder;
	  }
}
