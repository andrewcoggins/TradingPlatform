package brown.accounting.bid;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.tradeable.library.Tradeable;

/**
 * tests the complex bid class. 
 * C
 * @author andrew
 *
 */
public class ComplexBidTest {
  
  @Test 
  public void testComplexBid() { 
    Map<Set<Tradeable>, Double> complex = new HashMap<Set<Tradeable>, Double>();
    Set<Tradeable> trs = new HashSet<Tradeable>();
    trs.add(new Tradeable(0)); 
    trs.add(new Tradeable(1));
    complex.put(trs, 1.0);
    ComplexBid aComplexBid = new ComplexBid(complex);
    assertEquals(aComplexBid.bids, complex);
  }
}