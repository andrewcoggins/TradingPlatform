package brown.auction.rules.allocation.onesided;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.marketstate.IMarketState;
import brown.auction.marketstate.library.MarketState;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.allocation.SimpleHighestPriceAllocation;
import brown.communication.bid.IBidBundle;
import brown.communication.bid.library.OneSidedBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.TradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;
public class SimpleHighestPriceAllocationTest {

  @Test
  public void testSimpleHighestPriceAllocation() {
    IMarketState state = new MarketState(); 
    List<ITradeMessage> messages = new LinkedList<ITradeMessage>(); 
    
    assertEquals(state.getAllocation(), new HashMap<Integer, ICart>()); 
    
    Map<ICart, Double> cartsOne = new HashMap<ICart, Double>(); 
    Map<ICart, Double> cartsTwo = new HashMap<ICart, Double>(); 
    
    List<IItem> items = new LinkedList<IItem>(); 
    List<IItem> itemsTwo = new LinkedList<IItem>();
    
    items.add(new Item("a"));
    itemsTwo.add(new Item("b"));
    
    cartsOne.put(new Cart(items), 1.0); 
    cartsOne.put(new Cart(itemsTwo), 2.0);
    IBidBundle bb = new OneSidedBidBundle(cartsOne); 
    ITradeMessage messageOne = new TradeMessage(0, 0, 0, bb); 
    
    cartsTwo.put(new Cart(items), 2.0); 
    cartsTwo.put(new Cart(itemsTwo), 1.0); 
    IBidBundle bbTwo = new OneSidedBidBundle(cartsTwo); 
    ITradeMessage messageTwo = new TradeMessage(0, 1, 0, bbTwo);
    
    // so... create some trade messages. 
    messages.add(messageOne); 
    messages.add(messageTwo); 
    
    IAllocationRule highestPriceAllocation = new SimpleHighestPriceAllocation(); 
    
    highestPriceAllocation.setAllocation(state, messages);
    
    Map<Integer, List<ICart>> highest =  new HashMap<Integer, List<ICart>>(); 
    
    List<ICart> cListOne = new LinkedList<ICart>(); 
    cListOne.add(new Cart(items)); 
    
    List<ICart> cListTwo = new LinkedList<ICart>(); 
    cListTwo.add(new Cart(itemsTwo)); 
    
    highest.put(0, cListTwo); 
    highest.put(1, cListOne); 
    
    assertEquals(state.getAllocation(), highest); 
    
  }
  
  @Test
  public void testSimpleHighestPriceAllocationTwo() { 
    IMarketState state = new MarketState(); 
    List<ITradeMessage> messages = new LinkedList<ITradeMessage>(); 
    
    assertEquals(state.getAllocation(), new HashMap<Integer, ICart>()); 
    
    Map<ICart, Double> cartsOne = new HashMap<ICart, Double>(); 
    Map<ICart, Double> cartsTwo = new HashMap<ICart, Double>(); 
    
    List<IItem> items = new LinkedList<IItem>(); 
    List<IItem> itemsTwo = new LinkedList<IItem>();
    
    items.add(new Item("a"));
    itemsTwo.add(new Item("b"));
    
    cartsOne.put(new Cart(items), 2.0); 
    cartsOne.put(new Cart(itemsTwo), 2.0);
    IBidBundle bb = new OneSidedBidBundle(cartsOne); 
    ITradeMessage messageOne = new TradeMessage(0, 0, 0, bb); 
    
    cartsTwo.put(new Cart(items), 1.0); 
    cartsTwo.put(new Cart(itemsTwo), 1.0); 
    IBidBundle bbTwo = new OneSidedBidBundle(cartsTwo); 
    ITradeMessage messageTwo = new TradeMessage(0, 1, 0, bbTwo);
    
    // so... create some trade messages. 
    messages.add(messageOne); 
    messages.add(messageTwo); 
    
    IAllocationRule highestPriceAllocation = new SimpleHighestPriceAllocation(); 
    
    highestPriceAllocation.setAllocation(state, messages);
    
    Map<Integer, List<ICart>> highest =  new HashMap<Integer, List<ICart>>(); 
    
    List<ICart> cListOne = new LinkedList<ICart>(); 
    cListOne.add(new Cart(itemsTwo)); 
    cListOne.add(new Cart(items)); 
   
    highest.put(0, cListOne); 
    
    assertEquals(state.getAllocation(), highest); 
  }
  
}
