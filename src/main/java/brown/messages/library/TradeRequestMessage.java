package brown.messages.library;

import brown.agent.AbsAgent;
import brown.channels.IAgentChannel;

/**
 * For each open market, the server then sends all agents a TradeRequest,
 * which describes the current market state: quotes for one-sided markets,
 * and orderBooks for two-sided markets. Whenever an agent wants, it can
 * respond to the TradeRequest by constructing and sending the server a
 * BidBundle (using the methods bid, buy, sell, etc.) for that market.
 * 
 * @author lcamery
 */
public class TradeRequestMessage extends AbsMessage {
  
	public final IAgentChannel MARKET;
	
	public TradeRequestMessage() {
		super(null);
		MARKET = null;
	}

	public TradeRequestMessage(Integer ID, IAgentChannel market) {
		super(ID);
		this.MARKET = market;
	}

	@Override
	public void dispatch(AbsAgent agent) {
		this.MARKET.dispatchMessage(agent);
	}

}
