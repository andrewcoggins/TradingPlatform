package brown.rules.terminationconditions;

import brown.market.marketstate.IMarketState;


public interface ITerminationCondition {
  
  public void tisOver(IMarketState state);

}
