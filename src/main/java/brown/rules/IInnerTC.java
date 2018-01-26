package brown.rules;

import brown.market.marketstate.IMarketState;

/**
 * An inner termination condition manages when a single auction is over. 
 */
public interface IInnerTC {
  
  public void innerTerminated(IMarketState state);

  public void reset();

}
