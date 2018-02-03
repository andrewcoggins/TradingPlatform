package brown.rules.library;

import java.util.LinkedList;
import java.util.List;

import brown.market.marketstate.IMarketState;
import brown.rules.IGroupingRule;

public class OneGrouping implements IGroupingRule{

  @Override
  public void setGrouping(IMarketState state, List<Integer> agents) {
    List<List<Integer>> groups = new LinkedList<List<Integer>>();
    groups.add(agents);
    state.setGroups(groups);   
  }

  // Do this if you want to change groups on inner markets
  @Override
  public void reset() {    
  }
}
