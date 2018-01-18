package brown.accounting.bid;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.tradeable.library.MultiTradeable;

/**
 * tests the complex bid class. 
 * C
 * @author andrew
 *
 */
public class ComplexBidTest {
  
  @Test 
  public void testComplexBid() { 
    Map<Set<MultiTradeable>, Double> complex = new HashMap<Set<MultiTradeable>, Double>();
    Set<MultiTradeable> trs = new HashSet<MultiTradeable>();
    trs.add(new MultiTradeable(0)); 
    trs.add(new MultiTradeable(1));
    complex.put(trs, 1.0);
    ComplexBid aComplexBid = new ComplexBid(complex);
    assertEquals(aComplexBid.bids, complex);
  }
}