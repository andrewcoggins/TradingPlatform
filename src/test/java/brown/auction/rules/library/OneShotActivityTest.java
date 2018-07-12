package brown.auction.rules.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.marketstate.library.MarketState;
import brown.mechanism.bid.library.BidType;
import brown.mechanism.bidbundle.library.AuctionBidBundle;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.platform.messages.library.TradeMessage;

public class OneShotActivityTest {
  
  // test that one agent can't bid on a tradeable more than once.
  @Test
  public void testOneShotActivity() {
    List<ITradeable> tradeableList = new LinkedList<ITradeable>(); 
    tradeableList.add(new SimpleTradeable(0)); 
    tradeableList.add(new SimpleTradeable(1)); 
    MarketState state = new MarketState(0, tradeableList, null); 
    
    //simulate an agent bid.
    Map<ITradeable, BidType> bidMap = new HashMap<ITradeable, BidType>(); 
    bidMap.put(new SimpleTradeable(0), new BidType(1.0, 1)); 
    bidMap.put(new SimpleTradeable(1), new BidType(1.0, 1));
    TradeMessage message = new TradeMessage(0, new AuctionBidBundle(bidMap), 0, 0); 
    
    OneShotActivity aRule = new OneShotActivity(); 
    aRule.isAcceptable(state, message);
    assertEquals(state.getAcceptable(), true); 
    
    state.addBid(message);
    bidMap.put(new SimpleTradeable(0), new BidType(0.0, 1)); 
    bidMap.put(new SimpleTradeable(1), new BidType(2.0, 1));
    TradeMessage messageTwo = new TradeMessage(0, new AuctionBidBundle(bidMap), 0, 0);
    aRule.isAcceptable(state, messageTwo);
    assertEquals(state.getAcceptable(), false); 
  }
  
  //TODO: another case
  @Test
  public void testOneShotActivityTwo() {
    
  }
  
  public static void main(String[] args) {
    OneShotActivityTest t = new OneShotActivityTest(); 
    t.testOneShotActivity(); 
    t.testOneShotActivityTwo(); 
  }
}