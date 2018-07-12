package brown.auction.rules.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.marketstate.library.MarketState;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.platform.accounting.library.Order;

/**
 * Test for the clock payment rule.
 * Maybe come back later? 
 * @author andrew
 *
 */
public class ClockPaymentTest { 
  
  //want to test that when the alternate allocation has one person.
  @Test
  public void testClockPayment() {
    
    Map<ITradeable, List<Integer>> altAlloc = new HashMap<ITradeable, List<Integer>>(); 
    Map<ITradeable, List<Integer>> alloc = new HashMap<ITradeable, List<Integer>>(); 
    List<Integer> agentList = new LinkedList<Integer>();
    agentList.add(0); 
    // create allocation with exactly one agent being allocated.
    alloc.put(new SimpleTradeable(0), agentList); 
    agentList.add(1); 
    altAlloc.put(new SimpleTradeable(0), agentList); 
    // if the auction still has more than one active bidder, we can't set a payment to anyone.
    List<ITradeable> allGoods = new LinkedList<ITradeable>(); 
    allGoods.add(new SimpleTradeable(0)); 
    MarketState state = new MarketState(0, allGoods, null); 
    state.setAltAlloc(altAlloc);
    ClockPayment paymentRule = new ClockPayment(); 
    paymentRule.setOrders(state); 
    
    List<Order> emptyOrderList = new LinkedList<Order>(); 
    assertEquals(state.getPayments(), emptyOrderList); 
    
  }
  
  // test what happens if alt allocation has exactly one person, and regular allocation has
  // exactly one person.
  @Test
  public void testClockPaymentTwo() {
    
    Map<ITradeable, List<Integer>> altAlloc = new HashMap<ITradeable, List<Integer>>(); 
    Map<Integer, List<ITradeable>> alloc = new HashMap<Integer, List<ITradeable>>(); 
    List<Integer> agentList = new LinkedList<Integer>();
    agentList.add(0); 
    // create allocation with exactly one agent being allocated.
    List<ITradeable> tList = new LinkedList<ITradeable>(); 
    tList.add(new SimpleTradeable(0)); 
    //TODO? make allocation and alt allocation same kind of map
    alloc.put(0, tList); 
    altAlloc.put(new SimpleTradeable(0), agentList); 
    Map<ITradeable, Double> reserves = new HashMap<ITradeable, Double>(); 
    reserves.put(new SimpleTradeable(0), 100.0); 
    //set reserve prices for goods. 
    // if the auction still has more than one active bidder, we can't set a payment to anyone.
    List<ITradeable> allGoods = new LinkedList<ITradeable>(); 
    allGoods.add(new SimpleTradeable(0)); 
    MarketState state = new MarketState(0, allGoods, null); 
    state.setReserve(reserves);
    state.setAltAlloc(altAlloc);
    state.setAllocation(alloc);
    ClockPayment paymentRule = new ClockPayment(); 
    paymentRule.setOrders(state); 
    
    List<Order> orderList = new LinkedList<Order>();
    orderList.add(new Order(0, null, 100.0, 1, new SimpleTradeable(0))); 
    assertEquals(state.getPayments(), orderList); 
  }
  
  
  public static void main(String[] args) {
    ClockPaymentTest c = new ClockPaymentTest(); 
    c.testClockPayment(); 
    c.testClockPaymentTwo(); 
  }
}