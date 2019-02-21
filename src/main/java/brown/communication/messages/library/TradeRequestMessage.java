package brown.communication.messages.library;

import brown.communication.channel.IChannel;
import brown.communication.messages.ITradeRequestMessage;

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

	public TradeRequestMessage(Integer ID, IChannel channel) {
		super(ID, channel);
	}
	
}
