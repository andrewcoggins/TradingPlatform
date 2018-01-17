package brown.messages.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.accounting.bid.ComplexBid;
import brown.accounting.bid.SimpleBid;
import brown.accounting.bidbundle.library.ComplexBidBundle;
import brown.accounting.bidbundle.library.SimpleBidBundle;
import brown.tradeable.library.Good;

/**
 * test for the bid message. 
 * C
 * @author andrew
 *
 */
public class BidMessageTest {
  
  @Test
  public void testBidMessage() {
    Map<Good, MarketState> map = new HashMap<Good, MarketState>(); 
    Map<Set<Good>, MarketState> complexMap = new HashMap<Set<Good>, MarketState>(); 
    map.put(new Good(0), new MarketState(0, 1.0)); 
    SimpleBidBundle s = new SimpleBidBundle(map); 
    TradeMessage bm = new TradeMessage(0, s, 1, 2); 
    TradeMessage bmTwo = bm.safeCopy(2); 
    //test public fields
    assertEquals(bm.Bundle, s); 
    assertEquals(bm.AuctionID, new Integer(1)); 
    assertEquals(bm.AgentID, new Integer(2)); 
    assertEquals(bm, bmTwo); 
    //test complex case
    Set<Good> tSet = new HashSet<Good>();
    tSet.add(new Good(0)); 
    complexMap.put(tSet, new MarketState(0, 1.0));
    ComplexBidBundle c  = new ComplexBidBundle(complexMap, 0);
    TradeMessage cBid = new TradeMessage(0, c, 1, 2); 
    assertEquals(cBid, cBid.safeCopy(2)); 
    assertEquals(cBid.Bundle, c);
    assertEquals(cBid.Bundle.getBids(), c.getBids());
    ComplexBid com = (ComplexBid) cBid.Bundle.getBids();
    assertEquals(com.bids, c.getBids().bids);
  }
}