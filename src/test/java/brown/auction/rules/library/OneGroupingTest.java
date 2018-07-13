package brown.auction.rules.library;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.auction.marketstate.library.MarketState;
import brown.mechanism.tradeable.ITradeable;

public class OneGroupingTest {
  
  @Test
  public void testOneGrouping() {
    List<Integer> agents=  new LinkedList<Integer>(); 
    for (int i = 0; i < 10; i++) { 
      agents.add(i); 
    }
    MarketState state =  new MarketState(0, new LinkedList<ITradeable>(), null); 
    OneGrouping groupRule = new OneGrouping(); 
    groupRule.setGrouping(state, agents);
    // test that grouping within state is this. 
    List<List<Integer>> groups = new LinkedList<List<Integer>>(); 
    groups.add(agents);
    assertEquals(state.getGroups(), groups); 
  }
  
  public static void main(String[] args) {  
    OneGroupingTest t = new OneGroupingTest(); 
    t.testOneGrouping(); 
  }
}