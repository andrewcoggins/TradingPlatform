package brown.auction.rules.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.Test;

import brown.auction.marketstate.library.MarketState;
import brown.mechanism.bid.library.BidType;
import brown.mechanism.bidbundle.library.AuctionBidBundle;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.platform.messages.library.TradeMessage;

/**
 * Test for one shot termination condition. 
 * Dictates that auction ends after agents submit first bid. 
 * @author andrew
 *
 */
public class OneShotTerminationTest {
  
  @Test 
  public void testOneShotTermination() {
    // a trade message is received by the server
    
    Map<ITradeable, BidType> bidMap = new HashMap<ITradeable, BidType>();
    bidMap.put(new SimpleTradeable(0), new BidType(1.0, 1)); 
    TradeMessage tMessage = new TradeMessage(0, new AuctionBidBundle(bidMap), 0, 0); 
    MarketState state = new MarketState(0, new LinkedList<ITradeable>(bidMap.keySet()), null);
    OneShotTermination tCondition = new OneShotTermination(); 
    tCondition.isTerminated(state);
    //auction is initially not over
    assertEquals(state.getOver(), false);
    state.addBid(tMessage);
    tCondition.isTerminated(state);
    assertEquals(state.getOver(), true);
  }
  
  
  public static void main(String[] args) {
    OneShotTerminationTest t = new OneShotTerminationTest(); 
    t.testOneShotTermination();
  }
}