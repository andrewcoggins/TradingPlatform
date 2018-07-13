package brown.auction.rules.library;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IGroupingRule;
import brown.logging.library.Logging;

public class PairGrouping implements IGroupingRule{

  @Override
  public void setGrouping(IMarketState state, List<Integer> agents) {
    List<List<Integer>> groups = new LinkedList<List<Integer>>();
    
    // Make this shuffle if you want it to change from simulation to simulation
    Collections.sort(agents);
    Logging.log("AGENTS: " + agents.toString());
    
    if (agents.size() <= 2) {
      groups.add(new LinkedList<Integer>(agents));
    } else {
      int remainder  = agents.size() % 2;
      // 1 group of 3
      if (remainder == 1) {
        List<Integer> toAdd = new LinkedList<Integer>();
        toAdd.add(agents.remove(0));
        toAdd.add(agents.remove(0));
        toAdd.add(agents.remove(0));
        groups.add(toAdd);
      }
      while (agents.size() > 0) {
        List<Integer> toAdd = new LinkedList<Integer>();
        toAdd.add(agents.remove(0));            
        toAdd.add(agents.remove(0));                    
        groups.add(toAdd);
      }                      
    }
    state.setGroups(groups);    
    Logging.log("GROUPS: " + groups.toString());    
  }

}
