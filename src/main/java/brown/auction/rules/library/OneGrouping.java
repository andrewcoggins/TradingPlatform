package brown.auction.rules.library;

import java.util.LinkedList;
import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IGroupingRule;

public class OneGrouping implements IGroupingRule {

  @Override
  public void setGrouping(IMarketState state, List<Integer> agents) {
    List<List<Integer>> groups = new LinkedList<List<Integer>>();
    groups.add(agents);
    state.setGroups(groups);   
  }

}
