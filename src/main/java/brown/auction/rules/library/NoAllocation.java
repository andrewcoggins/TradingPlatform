package brown.auction.rules.library; 

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IAllocationRule;

public class NoAllocation implements IAllocationRule {

  @Override
  public void setAllocation(IMarketState state) {
    //noop
  }

  @Override
  public void reset() {
    //noop
  }
  
}