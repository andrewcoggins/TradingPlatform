package brown.accounting.bidbundle;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.accounting.bid.SimpleBid;
import brown.accounting.bidbundle.library.Allocation;
import brown.tradeable.library.Tradeable;

/**
 * test for the allocation class
 * C
 * @author andrew
 *
 */
public class AllocationTest {
  
  @Test
  public void testAllocation() {
    Map<Tradeable, MarketState> aMap = new HashMap<Tradeable, MarketState>();
    aMap.put(new Tradeable(0), new MarketState(1, 0.0)); 
    SimpleBid aBid = new SimpleBid(aMap);
    Allocation a = new Allocation(aBid); 
    Allocation otherA = new Allocation(aBid); 
    assertEquals(a.getBids(), aBid); 
    assertEquals(a, otherA);
  }
}