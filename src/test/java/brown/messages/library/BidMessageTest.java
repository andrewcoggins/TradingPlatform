package brown.messages.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.accounting.bidbundle.ComplexBidBundle;
import brown.accounting.bidbundle.SimpleBidBundle;
import brown.tradeable.library.Tradeable;

/**
 * test for the bid message. 
 * C
 * @author andrew
 *
 */
public class BidMessageTest {
  
  @Test
  public void testBidMessage() {
    Map<Tradeable, MarketState> map = new HashMap<Tradeable, MarketState>(); 
    Map<Set<Tradeable>, MarketState> complexMap = new HashMap<Set<Tradeable>, MarketState>(); 
    map.put(new Tradeable(0), new MarketState(0, 1.0)); 
    SimpleBidBundle s = new SimpleBidBundle(map); 
    BidMessage bm = new BidMessage(0, s, 1, 2); 
    BidMessage bmTwo = bm.safeCopy(2); 
    //test public fields
    assertEquals(bm.Bundle, s); 
    assertEquals(bm.AuctionID, new Integer(1)); 
    assertEquals(bm.AgentID, new Integer(2)); 
    assertEquals(bm, bmTwo); 
    //test complex case
    Set<Tradeable> tSet = new HashSet<Tradeable>();
    tSet.add(new Tradeable(0)); 
    complexMap.put(tSet, new MarketState(0, 1.0));
    ComplexBidBundle c  = new ComplexBidBundle(complexMap, 0);
    BidMessage cBid = new BidMessage(0, c, 1, 2); 
    assertEquals(cBid, cBid.safeCopy(2)); 
  }
}