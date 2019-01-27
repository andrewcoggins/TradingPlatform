package brown.auction.rules.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.marketstate.library.MarketState;
import brown.mechanism.channel.library.OneSidedChannel;
import brown.mechanism.messages.library.TradeRequestMessage;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;

/**
 * Test for query rule used in all sealed bid auctions. 
 * @author andrew
 *
 */
public class SimpleQueryTest {
  
  @Test
  public void testSimpleQuery() {
    
    List<ITradeable> tradeableList =  new LinkedList<ITradeable>(); 
    tradeableList.add(new SimpleTradeable(0)); 
    
    MarketState state = new MarketState(0, tradeableList, null);
    //set groups
    List<List<Integer>> allGroups = new LinkedList<List<Integer>>(); 
    List<Integer> oneGroup = new LinkedList<Integer>(); 
    oneGroup.add(0); 
    allGroups.add(oneGroup); 
    state.setGroups(allGroups);
    //create rule
    SimpleQuery queryRule = new SimpleQuery(); 
    queryRule.makeChannel(state);
    
    //map for groups
    Map<Integer, Integer> idToGroup = new HashMap<Integer, Integer>(); 
    idToGroup.put(0, 1); 
    TradeRequestMessage tr = new TradeRequestMessage(0, new OneSidedChannel(state.getID()), idToGroup);
    assertEquals(state.getTRequest(), tr); 
  }
  
  // test with a group of more than one person
  // TODO: 
  @Test
  public void testSimpleQueryTwo() { 
    
  }
  
  
  public static void main(String[] args) {
    SimpleQueryTest t = new SimpleQueryTest(); 
    
    t.testSimpleQuery(); 
  }
}