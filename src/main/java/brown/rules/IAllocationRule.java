package brown.rules;

import brown.market.marketstate.ICompleteState;

/**
 * An allocation rule allocates tradeables to agents.
 * @author andrew
 */
public interface IAllocationRule {

  public void setAllocation(ICompleteState state);

  public void reset();
  
}
