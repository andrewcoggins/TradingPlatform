package brown.rules.allocationrules;

import brown.market.marketstate.IMarketState;

/**
 * an allocation rule decides two things: 
 * 
 * @author andrew
 */
public interface IAllocationRule {
  public void setAllocation(IMarketState state);
}
