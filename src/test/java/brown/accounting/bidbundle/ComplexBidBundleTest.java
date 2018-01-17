package brown.accounting.bidbundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.accounting.bid.ComplexBid;
import brown.accounting.bidbundle.library.BundleType;
import brown.accounting.bidbundle.library.ComplexBidBundle;
import brown.tradeable.library.Tradeable;

/**
 * test for complex bid bundle. 
 * C
 * @author andrew
 *
 */
public class ComplexBidBundleTest {
  
  @Test
  public void testComplexBidBundle() {
    
    Map<Set<Tradeable>, MarketState> aBid = new HashMap<Set<Tradeable>, MarketState>();
    Set<Tradeable> tSet = new HashSet<Tradeable>();
    tSet.add(new Tradeable(0));
    tSet.add(new Tradeable(1));
    aBid.put(tSet, new MarketState(1, 1.0));
    ComplexBidBundle com = new ComplexBidBundle(aBid, 1);
    assertEquals(com.getType(), BundleType.Complex);
    assertEquals(com.getBids(), new ComplexBid(aBid));
    assertTrue(com.getCost() == 1.0);
    assertEquals(com, (ComplexBidBundle) com.wipeAgent(1));
  }
}