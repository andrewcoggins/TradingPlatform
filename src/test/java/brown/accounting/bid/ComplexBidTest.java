package brown.accounting.bid;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.tradeable.library.Good;

/**
 * tests the complex bid class. 
 * C
 * @author andrew
 *
 */
public class ComplexBidTest {
  
  @Test 
  public void testComplexBid() { 
    Map<Set<Good>, MarketState> complex = new HashMap<Set<Good>, MarketState>();
    Set<Good> trs = new HashSet<Good>();
    trs.add(new Good(0)); 
    trs.add(new Good(1));
    complex.put(trs, new MarketState(0, 1.0));
    ComplexBid aComplexBid = new ComplexBid(complex);
    assertEquals(aComplexBid.bids, complex);
  }
}