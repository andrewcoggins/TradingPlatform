package brown.rules;

import brown.market.marketstate.ICompleteState;

/**
 * An outer termination condition determines when a series of single 
 * auctions are over, per a sequential auction.
 */
public interface IOuterTC {
  
  public void outerTerminated(ICompleteState state);

}
