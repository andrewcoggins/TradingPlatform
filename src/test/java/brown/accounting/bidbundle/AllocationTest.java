package brown.accounting.bidbundle;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.accounting.bid.SimpleBid;
import brown.accounting.bidbundle.library.Allocation;
import brown.tradeable.library.Good;

/**
 * test for the allocation class
 * C
 * @author andrew
 *
 */
public class AllocationTest {
  
  @Test
  public void testAllocation() {
    Map<Good, MarketState> aMap = new HashMap<Good, MarketState>();
    aMap.put(new Good(0), new MarketState(1, 0.0)); 
    SimpleBid aBid = new SimpleBid(aMap);
    Allocation a = new Allocation(aBid); 
    Allocation otherA = new Allocation(aBid); 
    assertEquals(a.getBids(), aBid); 
    assertEquals(a, otherA);
  }
}