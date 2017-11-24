package brown.rules.terminationconditions.library; 

import brown.market.marketstate.IMarketState;
import brown.rules.terminationconditions.IInnerTC;

public class OneShotTermination implements IInnerTC {

  @Override
  public void innerTerminated(IMarketState state) {
    // TODO Auto-generated method stub
    boolean over = state.getBids().size() > 0; 
    state.setInnerOver(over);
  }

  
}