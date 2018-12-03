package brown.auction.rules.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.marketstate.library.MarketState;
import brown.mechanism.bid.library.BidType;
import brown.mechanism.bidbundle.library.OneSidedBidBundle;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.platform.accounting.library.Order;
import brown.platform.messages.library.TradeMessage;

/**
 * Test for simple second price payment. 
 * the allocated bidder should pay the price of the second highest bid. 
 * @author andrew
 *
 */
public class SimpleSecondPricePaymentTest {
  
  
  //test for no bidders
  @Test
  public void testSSPPayment() {
    
    List<ITradeable> tradeableList = new LinkedList<ITradeable>(); 
    for (int i = 0; i < 3; i++) {
      tradeableList.add(new SimpleTradeable(i)); 
    }
    
    MarketState state = new MarketState(0, tradeableList, null);
    SimpleSecondPricePayment paymentRule = new SimpleSecondPricePayment(); 
    // want to set an allocation
    Map<Integer, List<ITradeable>> allocation = new HashMap<Integer, List<ITradeable>>(); 
    // put agents in there
    // no bidders, no trademessages. 
    state.setAllocation(allocation);
    paymentRule.setOrders(state);
    // orders should be empty
    assertEquals(state.getPayments(), new LinkedList<Order>()); 
  }
  
  //test with only one bidder- should get good for free
  @Test 
  public void testSSPPaymentTwo() {
    
    List<ITradeable> tradeableList = new LinkedList<ITradeable>(); 
    for (int i = 0; i < 3; i++) {
      tradeableList.add(new SimpleTradeable(i)); 
    }
    
    MarketState state = new MarketState(0, tradeableList, null);
    SimpleSecondPricePayment paymentRule = new SimpleSecondPricePayment(); 
    //create trade messages
    Map<ITradeable, BidType> bidMap = new HashMap<ITradeable, BidType>(); 
    List<ITradeable> tList= new LinkedList<ITradeable>();
    for(int i = 0; i < 3; i++) {
      bidMap.put(new SimpleTradeable(i), new BidType(1.0, 1)); 
      tList.add(new SimpleTradeable(i)); 
    }
    OneSidedBidBundle bundle = new OneSidedBidBundle(bidMap); 
    TradeMessage message = new TradeMessage(0, bundle, 0, 0); 
    // adding agents' bid to market state. 
    state.addBid(message);
    List<Integer> singleGroup = new LinkedList<Integer>(); 
    singleGroup.add(0); 
    List<List<Integer>> groups = new LinkedList<List<Integer>>(); 
    groups.add(singleGroup); 
    state.setGroups(groups);
    // consider mockito, not doing allocation rule.
    // want to set an allocation
    Map<Integer, List<ITradeable>> allocation = new HashMap<Integer, List<ITradeable>>(); 
    allocation.put(0, tList); 
    // put agents in there
    // no bidders, no trademessages. 
    state.setAllocation(allocation);
    paymentRule.setOrders(state);
    List<Order> orderList = new LinkedList<Order>(); 
    for(int i = 0; i < 3; i++) { 
      orderList.add(new Order(0, null, 0.0, 1, new SimpleTradeable(i)));
    }
    // orders should be empty
    assertEquals(state.getPayments(), orderList);
  }
  
  //TODO 
  // test with two bidders
  @Test
  public void testSSPPaymentThree() {
    
  }
  
  // test with multiple bidders
  @Test
  public void testSSPPaymentFour() {
    
  }
  
  public static void main(String[] args) {
    SimpleSecondPricePaymentTest t = new SimpleSecondPricePaymentTest(); 
    t.testSSPPayment(); 
    t.testSSPPaymentTwo();
  }
}
