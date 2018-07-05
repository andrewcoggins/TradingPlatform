package brown.auction.rules.library; 

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IGroupingRule;
import brown.logging.library.Logging;

/**
 * Grouping policy that groups agents in groups of three or four, 
 * if possible.
 * @author acoggins
 *
 */
public class ThreeFourFiveGrouping implements IGroupingRule {
  
  @Override
  public void setGrouping(IMarketState state, List<Integer> agents) {
    List<List<Integer>> groups = new LinkedList<List<Integer>>();      

    // this effectively randomizes the groups
    Collections.sort(agents);
    Logging.log("AGENTS: " + agents.toString());
    
    if (agents.size() <= 5){
      // only have one group if less than 5
      groups.add(new LinkedList<Integer>(agents));
    } else {
      // else, split into groups of 4 and 3
      int remainder = agents.size() % 3;       
      // need as many groups of 4 as are in remainder
      int numFours = 0;
      while (numFours < remainder){
       List<Integer> toAdd = new LinkedList<Integer>();
       for (int j = 0; j < 4; j ++){
         toAdd.add(agents.remove(0));
       }     
       groups.add(toAdd);
       numFours++;
      }
      if ((agents.size() % 3) != 0){
        Logging.log("GROUPED ALLOCATION BUG: AGENTS NOT GROUPED IN THREES");
      }
      while (agents.size() > 0){
        List<Integer> toAdd = new LinkedList<Integer>();
        for (int j = 0; j < 3; j++){
          toAdd.add(agents.remove(0));            
        }
        groups.add(toAdd);
      }                
    }      
    state.setGroups(groups);
  }    
  
  @Override
  public void reset() {
  }    
}
