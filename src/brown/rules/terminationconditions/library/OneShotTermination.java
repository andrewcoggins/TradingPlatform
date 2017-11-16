package brown.rules.terminationconditions.library; 

import brown.market.marketstate.IMarketState;
import brown.rules.terminationconditions.ITerminationCondition;

public class OneShotTermination implements ITerminationCondition {

  @Override
  public void tisOver(IMarketState state) {
    // TODO Auto-generated method stubover
    boolean over = state.getBids().size() > 0; 
    state.setTOver(over);
  }
  
}