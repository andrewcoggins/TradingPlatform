package brown.communication.messages.library;

import brown.communication.bid.IBid;
import brown.communication.messages.ITradeMessage;

/**
 * Trade message is sent by the agent to the server
 * specifying an action withing a trading environment
 * This may be a bid or some other action. 
 * @author andrew
 *
 */
public class TradeMessage extends AbsTradeMessage implements ITradeMessage {
  
	/**
	 * void kryo
	 */
	public TradeMessage() {
		super();
	}

	/**
	 * Bid for when an agent wants to bid in an auction
	 * @param ID : bid ID
	 * @param bundle : bid bundle; varies by what the auction wants
	 * @param auctionID : auction's ID
	 * @param agentID : agent's ID; verified by server
	 */
	public TradeMessage(int messageID, Integer agentID, Integer auctionID, IBid bid) {
		super(messageID, agentID, auctionID, bid);
	}
  
}
