package brown.accounting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests the Market State class.
 * @author andrew
 *C
 */
public class MarketStateTest {
  
  @Test
  public void testMarketState() { 
    MarketState m = new MarketState(1, 2.0); 
    assertEquals(m.AGENTID, new Integer(1)); 
    assertTrue(m.PRICE == 2.0);
  }
}