package brown.auction.rules;

import brown.auction.marketstate.IMarketState;

/**
 * An inner termination condition manages when a single auction is over. 
 */
public interface ITerminationCondition {
  
  /**
   * determines the condition for an inner auction to be over. 
   * an example of an inner termined auction is an ascending auction.
   * @param state market internal state.
   */
  void checkTerminated(IMarketState state);

}
