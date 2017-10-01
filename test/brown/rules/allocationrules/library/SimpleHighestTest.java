package brown.rules.allocationrules.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.bundles.BidBundle;
import brown.bundles.MarketState;
import brown.bundles.SimpleBidBundle;
import brown.marketinternalstates.MarketInternalState;
import brown.marketinternalstates.SimpleInternalState;
import brown.messages.auctions.Bid;
import brown.tradeables.Asset;
import brown.valuable.library.Tradeable;

/**
 * test for simple highest bidder allocation. Testing that the rule does in fact
 * allocate each good in the auction to the highest bidder.
 * @author andrew
 *
 */
public class SimpleHighestTest {
  private int TRADEABLES = 1;
  private int AGENTS = 1;
  
  @Test
  public void testSimpleHighestAllocation() {
    
    Set<Asset> tradeables = new HashSet<Asset>();
    Map<Tradeable, MarketState> bundle = new HashMap<Tradeable, MarketState>();
    
    
    for(int i = 0; i < TRADEABLES; i++) {
      tradeables.add(new Asset(new Tradeable(i), 1));
      bundle.put(new Tradeable(i), new MarketState(i, 0.0));
    }
    MarketInternalState testState = new SimpleInternalState(0, tradeables);
    for(int i = 0; i < AGENTS; i++) {
      testState.addBid(new Bid(0, new SimpleBidBundle(bundle), 0, i));
    }
   SimpleHighestBidderAllocation testRule = new SimpleHighestBidderAllocation();
   BidBundle result = (SimpleBidBundle) testRule.getAllocation(testState);
   
   assertEquals(result, new SimpleBidBundle(bundle));  
  }
  
  
}