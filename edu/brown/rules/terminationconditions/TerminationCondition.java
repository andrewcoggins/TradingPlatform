package brown.rules.terminationconditions;

import brown.marketinternalstates.MarketInternalState;


public interface TerminationCondition {
  
  public void tisOver(MarketInternalState state);

}
