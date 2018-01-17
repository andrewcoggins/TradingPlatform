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
import brown.tradeable.library.Good;

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
    Map<Good, MarketState> aMap = new HashMap<Good, MarketState>();
    aMap.put(new Good(0), new MarketState(1, 2.0));
    SimpleBidBundle simple = new SimpleBidBundle(aMap);
    assertEquals(simple.getBids(), new SimpleBid(aMap)); 
    aMap.put(new Good(1), new MarketState(2, 2.0)); 
    SimpleBidBundle simpleTwo = new SimpleBidBundle(aMap);
    assertTrue(simpleTwo.getCost() == 4.0);
    assertEquals(simpleTwo.getType(), BundleType.Simple); 
    assertEquals(simpleTwo.getBid(new Good(0)), new MarketState(1, 2.0));
    assertEquals(simpleTwo.getBid(new Good(1)), new MarketState(2, 2.0));
    SimpleBidBundle newBundle = (SimpleBidBundle) simpleTwo.wipeAgent(1); 
    Map<Good, MarketState> aMapTwo = new HashMap<Good, MarketState>();
    aMapTwo.put(new Good(0), new MarketState(1, 2.0));
    aMapTwo.put(new Good(1), new MarketState(null, 2.0));
    SimpleBidBundle newBundleCopy = new SimpleBidBundle(aMapTwo);
    assertEquals(newBundle, newBundleCopy);
    
  }
}