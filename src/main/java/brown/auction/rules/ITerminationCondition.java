package brown.auction.rules;

import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.communication.messages.ITradeMessage;

/**
 * An inner termination condition manages when a single auction is over. 
 */
public interface ITerminationCondition {
  
  /**
   * determines the condition for an inner auction to be over. 
   * an example of an inner termined auction is an ascending auction.
   * @param state market internal state.
   * @param messages the current bids in the active market- the termination condition may rely on these. 
   */
  void checkTerminated(IMarketState state, List<ITradeMessage> messages);

}
