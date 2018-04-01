package brown.rules.library; 

import brown.market.marketstate.IMarketState;
import brown.rules.IQueryRule;

public class SMRAQuery implements IQueryRule {

  @Override
  public void makeChannel(IMarketState state) {
    // make a channel that gives everyone the round's prices.
    // may need a new channel... 
    // first solve the increment setting problem.
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub
    
  }
  
}