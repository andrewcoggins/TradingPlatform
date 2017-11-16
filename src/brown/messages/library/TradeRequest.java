package brown.messages.library;

import brown.agent.AbsAgent;
import brown.channels.MechanismType;
import brown.channels.agent.IAgentChannel;
import brown.messages.AbsMessage;

/**
 * For each open market, the server then sends all agents a TradeRequest,
 * which describes the current market state: quotes for one-sided markets,
 * and orderBooks for two-sided markets. Whenever an agent wants, it can
 * respond to the TradeRequest by constructing and sending the server a
 * BidBundle (using the methods bid, buy, sell, etc.) for that market.
 * @author lcamery
 *
 */
public class TradeRequest extends AbsMessage {
	public final IAgentChannel MARKET;
	public final MechanismType MECHANISM;
	
	public TradeRequest() {
		super(null);
		MARKET = null;
		MECHANISM = null;
	}

	public TradeRequest(Integer ID, IAgentChannel market, MechanismType mechanism) {
		super(ID);
		this.MARKET = market;
		this.MECHANISM = mechanism;
	}

	@Override
	public void dispatch(AbsAgent agent) {
		this.MARKET.dispatchMessage(agent);
	}

}
