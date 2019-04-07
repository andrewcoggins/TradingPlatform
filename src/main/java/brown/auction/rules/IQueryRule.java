package brown.auction.rules;

import brown.auction.marketstate.IMarketState;

/**
 * A Query rule controls the agent channel and trade request that are sent
 * to agents.
 * @author andrew
 *
 */
public interface IQueryRule {

  /**
   * constructs an agent channel for sending to agent.
   * This comes with a trade request.
   * @param state market state.
   */
  void makeTradeRequest(IMarketState state);

  
}
