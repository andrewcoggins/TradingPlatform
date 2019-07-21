package brown.auction.rules.payment.onesided;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.marketstate.IMarketState;
import brown.auction.marketstate.library.MarketState;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.IPaymentRule;
import brown.auction.rules.allocation.onesided.SimpleHighestPriceAllocation;
import brown.communication.bid.IBidBundle;
import brown.communication.bid.library.OneSidedBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.TradeMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.library.AccountUpdate;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;

public class SecondPricePaymentTest {

  // single item case
  @Test
  public void testSecondPricePayemnt() {

    final int NUMAGENTS = 10;

    IMarketState state = new MarketState();
    
    List<IItem> items = new LinkedList<IItem>();
    
    items.add(new Item("testItem", 1));
    
    ICart bidCart = new Cart(items);
    
    List<ITradeMessage> tradeMessages = new LinkedList<ITradeMessage>();

    for (int i = 0; i < NUMAGENTS; i++) {
      
      Map<ICart, Double> bidMap = new HashMap<ICart, Double>();
      
      bidMap.put(bidCart, 1.0 * i);
      
      IBidBundle bids = new OneSidedBidBundle(bidMap);
      
      ITradeMessage aTradeMessage = new TradeMessage(0, i, 0, bids);
      
      tradeMessages.add(aTradeMessage);
      
    }

    IAllocationRule aRule = new SimpleHighestPriceAllocation();
    
    IPaymentRule pRule = new SecondPricePayment();
    
    aRule.setAllocation(state, tradeMessages);
    
    pRule.setOrders(state, tradeMessages);
    
    List<IItem> expectedItems = new LinkedList<IItem>();
    
    expectedItems.add(new Item("testItem", 1));
    
    ICart expectedCart = new Cart(expectedItems);
    
    List<IAccountUpdate> expectedList = new LinkedList<IAccountUpdate>();

    IAccountUpdate expected = new AccountUpdate(9, 8.0, expectedCart);
    
    expectedList.add(expected);
    
    assertEquals(state.getPayments(), expectedList);
    
  }

  // test case of multiple items
  @Test
  public void testSecondPricePaymentTwo() {

    final int NUMAGENTS = 10;
    
    final int NUMITEMS = 5; 

    IMarketState state = new MarketState();
    
    List<ICart> bidCarts = new LinkedList<ICart>(); 
    
    for (int i = 0; i < NUMITEMS; i++) {
      
      List<IItem> items = new LinkedList<IItem>();
      
      items.add(new Item("testItem " + i, 1));
      
      bidCarts.add(new Cart(items)); 
    }
    
    List<ITradeMessage> tradeMessages = new LinkedList<ITradeMessage>();

    for (int i = 0; i < NUMAGENTS; i++) {
      
      Map<ICart, Double> bidMap = new HashMap<ICart, Double>();
      
      for (int j = 0; j < NUMITEMS; j++) {
        
        bidMap.put(bidCarts.get(j), (1.0 * i) - j);
        
      }
      
      IBidBundle bids = new OneSidedBidBundle(bidMap);
      
      ITradeMessage aTradeMessage = new TradeMessage(0, i, 0, bids);
      
      tradeMessages.add(aTradeMessage);
      
    }

    IAllocationRule aRule = new SimpleHighestPriceAllocation();
    
    IPaymentRule pRule = new SecondPricePayment();
    
    aRule.setAllocation(state, tradeMessages);

    pRule.setOrders(state, tradeMessages);
     
    List<IAccountUpdate> expectedList = new LinkedList<IAccountUpdate>();
    
    for (int i = 0; i < NUMITEMS; i++) {
      
      List<IItem> expectedItems = new LinkedList<IItem>();
      
      expectedItems.add(new Item("testItem " + i, 1));
      
      ICart expectedCart = new Cart(expectedItems);
      
      IAccountUpdate expected = new AccountUpdate(9, 8.0 - i, expectedCart);
      
      expectedList.add(expected);
      
    }
    
    for (IAccountUpdate update : expectedList) {
      assertTrue(state.getPayments().contains(update)); 
    }
    
    assertEquals(state.getPayments().size(), expectedList.size()); 
    
  }

}
