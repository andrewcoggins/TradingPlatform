package brown.accounting.bidbundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import brown.accounting.BundleType;
import brown.accounting.MarketState;
import brown.accounting.bid.SimpleBid;
import brown.accounting.bidbundle.library.SimpleBidBundle;
import brown.tradeable.library.Tradeable;

/**
 * test for simple bid bundle. 
 * C
 * @author andrew
 *
 */
public class SimpleBidBundleTest {
  
  @Test
  public void testSimpleBidBundle() {
    //basic functionality
    Map<Tradeable, MarketState> aMap = new HashMap<Tradeable, MarketState>();
    aMap.put(new Tradeable(0), new MarketState(1, 2.0));
    SimpleBidBundle simple = new SimpleBidBundle(aMap);
    assertEquals(simple.getBids(), new SimpleBid(aMap)); 
    aMap.put(new Tradeable(1), new MarketState(2, 2.0)); 
    SimpleBidBundle simpleTwo = new SimpleBidBundle(aMap);
    assertTrue(simpleTwo.getCost() == 4.0);
    assertEquals(simpleTwo.getType(), BundleType.Simple); 
    assertEquals(simpleTwo.getBid(new Tradeable(0)), new MarketState(1, 2.0));
    assertEquals(simpleTwo.getBid(new Tradeable(1)), new MarketState(2, 2.0));
    SimpleBidBundle newBundle = (SimpleBidBundle) simpleTwo.wipeAgent(1); 
    Map<Tradeable, MarketState> aMapTwo = new HashMap<Tradeable, MarketState>();
    aMapTwo.put(new Tradeable(0), new MarketState(1, 2.0));
    aMapTwo.put(new Tradeable(1), new MarketState(null, 2.0));
    SimpleBidBundle newBundleCopy = new SimpleBidBundle(aMapTwo);
    assertEquals(newBundle, newBundleCopy);
    
  }
}