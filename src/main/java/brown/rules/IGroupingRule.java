package brown.rules;

import java.util.List;

import brown.market.marketstate.IMarketState;

/**
 * An allocation rule allocates tradeables to agents.
 * @author andrew
 */
public interface IGroupingRule {

  // This will set the groups field in state, which is a list of lists of agent IDs.
  public void setGrouping(IMarketState state, List<Integer> agents);
  
  // This will decide what happens to the group
  public void reset();    
}
