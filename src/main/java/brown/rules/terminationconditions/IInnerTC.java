package brown.rules.terminationconditions;

import brown.market.marketstate.IMarketState;

/*
 * An inner Termination condition manages when a single auction is over. 
 */
public interface IInnerTC {
  
  public void innerTerminated(IMarketState state);

}
