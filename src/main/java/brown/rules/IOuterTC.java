package brown.rules;

import brown.market.marketstate.IMarketState;

/**
 * An outer termination condition determines when a series of single 
 * auctions are over, per a sequential auction.
 */
public interface IOuterTC {
  
  /**
   * determines when an outer auction (sequential auction) is over.
   * @param state market internal state.
   */
  public void outerTerminated(IMarketState state);

}
