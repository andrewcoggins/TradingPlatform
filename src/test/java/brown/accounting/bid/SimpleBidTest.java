package brown.accounting.bid;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.tradeable.library.Good;

/**
 * tests the simple bid class. 
 * C
 * @author andrew
 *
 */
public class SimpleBidTest {
  
  @Test 
  public void testSimpleBid() {
    Map<Good, MarketState> simple = new HashMap<Good, MarketState>();
    simple.put(new Good(0), new MarketState(1, 1.0)); 
    simple.put(new Good(1), new MarketState(1, 1.0)); 
    SimpleBid simpleB = new SimpleBid(simple);
    assertEquals(simpleB.bids, simple);
  }
}