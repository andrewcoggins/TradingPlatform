package brown.auction.rules.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.Test;

import brown.auction.marketstate.library.MarketState;
import brown.mechanism.bid.library.BidType;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;

/**
 * Test for one shot termination condition. 
 * Dictates that auction ends after agents submit first bid. 
 * @author andrew
 *
 */
public class XTimeTerminationTest {
  
  @Test 
  public void testXTimeTermination() throws InterruptedException {
    // a trade message is received by the server
    
    Map<ITradeable, BidType> bidMap = new HashMap<ITradeable, BidType>();
    bidMap.put(new SimpleTradeable(0), new BidType(1.0, 1)); 
    MarketState state = new MarketState(0, new LinkedList<ITradeable>(bidMap.keySet()), null);
    XTimeTermination tCondition = new XTimeTermination(5); 
    Thread.sleep(4000); 
    tCondition.isTerminated(state);
    //auction is initially not over
    assertEquals(state.getOver(), false);
    Thread.sleep(1000); 
    tCondition.isTerminated(state);
    assertEquals(state.getOver(), true);
  }
  
  
  public static void main(String[] args) throws InterruptedException {
    XTimeTerminationTest t = new XTimeTerminationTest(); 
    t.testXTimeTermination();
  }
}