package brown.auction.rules.library; 

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.ITerminationCondition;

public class NoBiddingTermination implements ITerminationCondition {

  // If there are bids then a round has happened
  @Override
  public void isTerminated(IMarketState state) {
    state.setOver(true);
  }

  @Override
  public void reset() {
  }  
}