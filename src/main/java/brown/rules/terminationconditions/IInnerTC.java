package brown.rules.terminationconditions;

import brown.market.marketstate.ICompleteState;

/**
 * An inner Termination condition manages when a single auction is over. 
 */
public interface IInnerTC {
  
  public void innerTerminated(ICompleteState state);

}
