package brown.rules.allocationrules;

import brown.market.marketstate.ICompleteState;

/**
 * An allocation rule allocates tradeables to agents.
 * @author andrew
 */
public interface IAllocationRule {

  public void setAllocation(ICompleteState state);
  
}
