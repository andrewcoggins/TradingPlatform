package brown.rules.terminationconditions.library; 

import brown.market.marketstate.ICompleteState;
import brown.rules.terminationconditions.IInnerTC;

public class OneShotTermination implements IInnerTC {

  @Override
  public void innerTerminated(ICompleteState state) {
    // TODO Auto-generated method stub
    boolean over = state.getBids().size() > 0; 
    state.setInnerOver(over);
  }

  
}