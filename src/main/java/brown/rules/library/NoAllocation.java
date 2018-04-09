package brown.rules.library; 

import brown.market.marketstate.IMarketState;
import brown.rules.IAllocationRule;

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