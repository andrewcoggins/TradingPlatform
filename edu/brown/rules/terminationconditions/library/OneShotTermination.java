package brown.rules.terminationconditions.library; 

import brown.marketinternalstates.MarketInternalState;
import brown.rules.terminationconditions.TerminationCondition;

public class OneShotTermination implements TerminationCondition {

  @Override
  public void tisOver(MarketInternalState state) {
    // TODO Auto-generated method stub
    boolean over = state.getBids().size() > 0; 
    state.setTOver(over);
  }
  
}