package brown.auction.rules;

import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.communication.messages.ITradeMessage;
import brown.platform.tradeable.ITradeable;

/**
 * A Query rule controls the agent channel and trade request that are sent
 * to agents.
 * @author andrew
 *
 */

//what if bidding in open outcry and sealed bid at the same time? 
//need to give some information on how to respond.
//maybe send a bidType? 
//send a representation of the market's tradeables, if any.
// 

public interface IQueryRule {

  /**
   * constructs an agent channel for sending to agent.
   * This comes with a trade request.
   * @param state market state.
   */
  void makeTradeRequest(IMarketState state, Map<String, List<ITradeable>> tradeables, List<ITradeMessage> bids, Integer agentID);

  
}
