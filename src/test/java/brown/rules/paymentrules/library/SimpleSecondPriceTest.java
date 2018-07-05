package brown.rules.paymentrules.library;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.auction.rules.library.SimpleSecondPricePayment;
import brown.mechanism.bidbundle.AuctionBidBundle;
import brown.mechanism.tradeable.MultiTradeable;

/**
 * simple second price payment rule test.
 * I
 * @author andrew
 *
 */
public class SimpleSecondPriceTest {
  
  @Test
  public void testSimpleSecondPrice() { 
    Map<MultiTradeable, MarketState> aMap = new HashMap<MultiTradeable, MarketState>();
    AuctionBidBundle al = new AuctionBidBundle(aMap);
    MarketState state = new MarketState(0, aMap.keySet());
    state.setAllocation(al);
    
    SimpleSecondPricePayment sim = new SimpleSecondPricePayment();
  }
}