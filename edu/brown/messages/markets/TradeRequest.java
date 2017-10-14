package brown.messages.markets;

import brown.agent.Agent;

import brown.markets.IAgentChannel;
import brown.markets.MechanismType;
import brown.messages.Message;

/**
 * For each open market, the server then sends all agents a TradeRequest,
 * which describes the current market state: quotes for one-sided markets,
 * and orderBooks for two-sided markets. Whenever an agent wants, it can
 * respond to the TradeRequest by constructing and sending the server a
 * BidBundle (using the methods bid, buy, sell, etc.) for that market.
 * @author lcamery
 *
 */
public class TradeRequest extends Message {
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
	public void dispatch(Agent agent) {
		this.MARKET.dispatchMessage(agent);
	}

}
