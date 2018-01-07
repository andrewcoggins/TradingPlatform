package brown.market.marketstate.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.accounting.BundleType;
import brown.accounting.MarketState;
import brown.accounting.Order;
import brown.accounting.bid.SimpleBid;
import brown.accounting.bidbundle.Allocation;
import brown.accounting.bidbundle.ComplexBidBundle;
import brown.accounting.bidbundle.SimpleBidBundle;
import brown.messages.library.BidMessage;
import brown.messages.library.BidRequestMessage;
import brown.tradeable.library.Tradeable;

/**
 * tests the internal state class. 
 * I
 * @author andrew
 *
 */
public class InternalStateTest {
  
  @Test
  public void testInternalState() {
    Set<Tradeable> tradeables = new HashSet<Tradeable>();
    tradeables.add(new Tradeable(0));
    tradeables.add(new Tradeable(1));
    tradeables.add(new Tradeable(2));
    InternalState state = new InternalState(0, tradeables);
    //going to go through these in order to the best of my ability. 
    Map<Tradeable, MarketState> map = new HashMap<Tradeable, MarketState>();
    map.put(new Tradeable(1), new MarketState(0, 1.0));
    BidMessage aBid = new BidMessage(0, new SimpleBidBundle(map), 0, 0);
    //addBid, getBids
    state.addBid(aBid);
    assertEquals((SimpleBid) state.getBids().get(0).Bundle.getBids(), new SimpleBid(map));
    //clear
    state.clear();
    assertEquals(state.getBids().size(), 0);
    //getTradeables
    assertEquals(state.getTradeables(), tradeables);
    //getID
    assertEquals(state.getID(), new Integer(0));
    //setLastPayments, getLastPayments
    List<Order> lastPayments = new LinkedList<Order>();
    lastPayments.add(new Order(1, 0, 100.0, 5, new Tradeable(2)));
    state.setLastPayments(lastPayments);
    assertEquals(state.getLastPayments(), lastPayments);
    //tick, getTicks
    state.tick((long) 0.0);
    assertTrue(state.getTicks() == 1); 
    //setReserve, getBundleReserve
    SimpleBidBundle aBundle = new SimpleBidBundle(map);
    state.setReserve(aBundle);
    SimpleBidBundle gottenBundle = (SimpleBidBundle) state.getbundleReserve();
    assertEquals(aBundle, gottenBundle);
    //complex case
    Map<Set<Tradeable>, MarketState> complexMap = new HashMap<Set<Tradeable>, MarketState>();
    complexMap.put(tradeables, new MarketState(0, 1.0));
    ComplexBidBundle comMap = new ComplexBidBundle(complexMap, 0);
    state.setReserve(comMap); 
    ComplexBidBundle gottenCom = (ComplexBidBundle) state.getbundleReserve();
    assertEquals(comMap, gottenCom);
    //clearBids
    state.addBid(aBid);
    state.clearBids();
    assertTrue(state.getBids().size() == 0);
    //getIncrement
    assertTrue(state.getIncrement() == 20.0);
    //setMaximizingRevenue, isMaximizingRevenue
    state.setMaximizingRevenue(true);
    assertTrue(state.isMaximizingRevenue());
    state.setMaximizingRevenue(false);
    assertTrue(!state.isMaximizingRevenue());
    //getEligibility (appears to not be functional)
    assertTrue(state.getEligibility() == 0);
    // allocation rules.
    // get/set time
    state.setTime((long) 1.0);
    assertTrue(state.getTime() == (long) 1.0);
    //get/set allocation
    Map<Tradeable, MarketState> allMap = new HashMap<Tradeable, MarketState>();
    allMap.put(new Tradeable(0), new MarketState(1, 2.0));
    Allocation a = new Allocation(new SimpleBid(allMap));
    assertEquals(a, allMap);
    BidRequestMessage b = new BidRequestMessage(0, 0, BundleType.Complex, comMap, tradeables);
    
    
    
  }
}