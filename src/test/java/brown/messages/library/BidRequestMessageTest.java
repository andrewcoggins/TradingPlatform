package brown.messages.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.accounting.bidbundle.library.BundleType;
import brown.accounting.bidbundle.library.SimpleBidBundle;
import brown.tradeable.library.MultiTradeable;

/**
 * test for the bid request message. 
 * C
 * @author andrew
 *
 */
public class BidRequestMessageTest {
  //not being used.
  
  @Test
  public void testBidRequestMessage() {
    Map<MultiTradeable, MarketState> aMap = new HashMap<MultiTradeable, MarketState>();
    aMap.put(new MultiTradeable(0), new MarketState(1, 0.0));
    SimpleBidBundle s = new SimpleBidBundle(aMap);
    Set<MultiTradeable> goods = new HashSet<MultiTradeable>();
    goods.add(new MultiTradeable(0));
    BidRequestMessage b = new BidRequestMessage(0, 0, BundleType.Simple, s, goods);
    assertTrue(b.AuctionID == 0);
    assertEquals(b.TYPE, BundleType.Simple);
    assertEquals(b.Current, s);
    SimpleBidBundle sCopy = (SimpleBidBundle) b.Current; 
    assertEquals(s, sCopy);
  }
}