package brown.rules;

import brown.market.marketstate.IMarketState;

/**
 * An allocation rule allocates tradeables to agents.
 * @author andrew
 */
public interface IAllocationRule {

  public void setAllocation(IMarketState state);

  public void reset();
  
}
