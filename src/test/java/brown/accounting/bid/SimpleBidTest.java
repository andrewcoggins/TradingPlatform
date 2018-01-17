package brown.accounting.bid;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.tradeable.library.Tradeable;

/**
 * tests the simple bid class. 
 * C
 * @author andrew
 *
 */
public class SimpleBidTest {
  
  @Test 
  public void testSimpleBid() {
    Map<Tradeable, MarketState> simple = new HashMap<Tradeable, MarketState>();
    simple.put(new Tradeable(0), new MarketState(1, 1.0)); 
    simple.put(new Tradeable(1), new MarketState(1, 1.0)); 
    SimpleBid simpleB = new SimpleBid(simple);
    assertEquals(simpleB.bids, simple);
  }
}