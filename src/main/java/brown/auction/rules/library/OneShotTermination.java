package brown.auction.rules.library; 

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.ITerminationCondition;

public class OneShotTermination implements ITerminationCondition {

  // If there are bids then a round has happened
  @Override
  public void isTerminated(IMarketState state) {    
    boolean over = state.getBids().size() > 0; 
    state.setOver(over);
  }

  @Override
  public void reset() {
  }  
  
}