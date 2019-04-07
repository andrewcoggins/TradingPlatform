package brown.communication.messages.library;

import java.util.List;

import brown.communication.bid.library.BidType;
import brown.communication.messages.ITradeRequestMessage;
import brown.user.agent.IAgent;

/**
 * Trade request message is sent by an open market 
 * to an agent to prompt bidding in that market. 
 * An agent typically sends a trademessage in response
 * to a TradeRequestMessage
 * @author andrew
 *
 */
public class TradeRequestMessage extends AbsTradeRequestMessage implements ITradeRequestMessage {

	/**
	 * void kryo 
	 */
	public TradeRequestMessage() {
		super();
	}

	public TradeRequestMessage(Integer messageID, Integer agentID, BidType bidType, List<String> tradeableTypes) {
	  super(messageID, agentID, bidType, tradeableTypes); 
	}
	
  @Override
  public void agentDispatch(IAgent agent) {
    // TODO Auto-generated method stub
  }

}
