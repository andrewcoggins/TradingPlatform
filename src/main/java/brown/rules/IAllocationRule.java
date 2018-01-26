package brown.rules;

import java.util.List;

import brown.market.marketstate.IMarketState;

/**
 * An allocation rule allocates tradeables to agents.
 * @author andrew
 */
public interface IAllocationRule {

  public void setAllocation(IMarketState state);

  // groups up agents if this is applicable
  public void setGroups(IMarketState state, List<Integer> agents);
  
  public void reset();  
}
