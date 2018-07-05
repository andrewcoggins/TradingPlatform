package brown.auction.rules.library; 

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IInnerTC;

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