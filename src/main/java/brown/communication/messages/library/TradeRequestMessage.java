package brown.communication.messages.library;

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

  @Override
  public void agentDispatch(IAgent agent) {
    // TODO Auto-generated method stub
    
  }

}
