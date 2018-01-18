package brown.rules.terminationconditions.library; 

import brown.market.marketstate.ICompleteState;
import brown.rules.terminationconditions.IInnerTC;

public class OneShotTermination implements IInnerTC {

  // If there are bids then a round has happened
  @Override
  public void innerTerminated(ICompleteState state) {
    boolean over = state.getBids().size() > 0; 
    state.setInnerOver(over);
  }

  
}