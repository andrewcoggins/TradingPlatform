package brown.accounting.bid;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import brown.tradeable.library.MultiTradeable;

/**
 * tests the simple bid class. 
 * C
 * @author andrew
 *
 */
public class SimpleBidTest {
  
  @Test 
  public void testSimpleBid() {
    Map<MultiTradeable, Double> simple = new HashMap<MultiTradeable, Double>();
    simple.put(new MultiTradeable(0), 1.0); 
    simple.put(new MultiTradeable(1), 2.0); 
    AuctionBid simpleB = new AuctionBid(simple);
    assertEquals(simpleB.bids, simple);
  }
}