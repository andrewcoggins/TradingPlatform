package brown.auction.rules.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.auction.marketstate.library.MarketState;
import brown.mechanism.tradeable.ITradeable;

public class ThreeFourFiveGroupingTest {
  
  // group with four or less agents.
  @Test
  public void testThreeFourFiveGrouping() {
    List<Integer> agents=  new LinkedList<Integer>(); 
    for (int i = 0; i < 5; i++) { 
      agents.add(i); 
    }
    MarketState state =  new MarketState(0, new LinkedList<ITradeable>(), null); 
    ThreeFourFiveGrouping groupRule = new ThreeFourFiveGrouping(); 
    groupRule.setGrouping(state, agents);
    // test that grouping within state is this. 
    List<List<Integer>> groups = new LinkedList<List<Integer>>(); 
    groups.add(agents);
    assertEquals(state.getGroups(), groups); 
  }
  
  // group with > 5, but %3
  @Test
  public void testThreeFourFiveGroupingTwo() {
    List<Integer> agents=  new LinkedList<Integer>(); 
    for (int i = 0; i < 9; i++) { 
      agents.add(i); 
    }
    MarketState state =  new MarketState(0, new LinkedList<ITradeable>(), null); 
    ThreeFourFiveGrouping groupRule = new ThreeFourFiveGrouping(); 
    groupRule.setGrouping(state, agents);
    //1. no insertions or deletions
    //2. each group has three people.
    for (int i = 0; i < 3; i++) {
      assertEquals(state.getGroups().get(i).size(), 3); 
    }
    for (int a : agents) {  
      boolean isIn = false; 
      for (List<Integer> group : state.getGroups()) {
        if (group.contains(a)) {
          isIn = true; 
          break; 
        }
      }
      assertTrue(isIn); 
    }
  }
  
  public static void main(String[] args) {  
    ThreeFourFiveGroupingTest t = new ThreeFourFiveGroupingTest(); 
    t.testThreeFourFiveGrouping(); 
    t.testThreeFourFiveGroupingTwo(); 
  }
}