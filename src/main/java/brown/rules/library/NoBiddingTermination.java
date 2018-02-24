package brown.rules.library; 

import brown.market.marketstate.IMarketState;
import brown.rules.IInnerTC;

public class NoBiddingTermination implements IInnerTC {

  // If there are bids then a round has happened
  @Override
  public void innerTerminated(IMarketState state) {
    state.setInnerOver(true);
  }

  @Override
  public void reset() {
  }  
}