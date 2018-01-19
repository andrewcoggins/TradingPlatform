package brown.rules.library; 

import brown.market.marketstate.ICompleteState;
import brown.rules.IInnerTC;

public class OneShotTermination implements IInnerTC {

  // If there are bids then a round has happened
  @Override
  public void innerTerminated(ICompleteState state) {
    boolean over = state.getBids().size() > 0; 
    state.setInnerOver(over);
  }

  @Override
  public void reset() {
  }  
}