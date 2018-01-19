package brown.rules.terminationconditions;

import brown.market.marketstate.ICompleteState;

/**
 * An inner termination condition manages when a single auction is over. 
 */
public interface IInnerTC {
  
  public void innerTerminated(ICompleteState state);

  public void reset();

}
