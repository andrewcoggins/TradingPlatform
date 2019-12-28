package brown.auction.rules.activity.onesided;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.marketstate.IMarketState;
import brown.auction.marketstate.library.MarketState;
import brown.auction.rules.IActivityRule;
import brown.auction.rules.activity.SimpleOneShotActivity;
import brown.communication.bid.IBidBundle;
import brown.communication.bid.library.OneSidedBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.TradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;

public class SimpleOneShotActivityTest {

  // test that not acceptable for agent to add more than one message.
  @Test
  public void testOneShotActivity() {
    
    Map<ICart, Double> cartsOne = new HashMap<ICart, Double>(); 
    Map<ICart, Double> cartsTwo = new HashMap<ICart, Double>(); 
    
    List<IItem> items = new LinkedList<IItem>(); 
    List<IItem> itemsTwo = new LinkedList<IItem>();
    List<IItem> itemsThree = new LinkedList<IItem>(); 
    
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
    
    List<ITradeMessage> current = new LinkedList<ITradeMessage>(); 
    current.add(messageOne); 
    current.add(messageTwo); 
    
    IActivityRule actRule = new SimpleOneShotActivity();
    
    itemsThree.add(new Item("a")); 
    itemsThree.add(new Item("b")); 
    
    ICart cartThree = new Cart(itemsThree); 
    
    IMarketState mState = new MarketState();
    
    actRule.isAcceptable(mState, messageOne, current, cartThree);
    
    assertTrue(!mState.getAcceptable()); 
    
  }
  
  // test that is acceptable for agent to bid. 
  @Test
  public void testOneShotActivityTwo() {
    
    Map<ICart, Double> cartsOne = new HashMap<ICart, Double>(); 
    Map<ICart, Double> cartsTwo = new HashMap<ICart, Double>(); 
    
    List<IItem> items = new LinkedList<IItem>(); 
    List<IItem> itemsTwo = new LinkedList<IItem>();
    List<IItem> itemsThree = new LinkedList<IItem>(); 
    
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
    
    List<ITradeMessage> current = new LinkedList<ITradeMessage>(); 
    current.add(messageOne); 
    
    IActivityRule actRule = new SimpleOneShotActivity();
    
    itemsThree.add(new Item("a")); 
    itemsThree.add(new Item("b")); 
    
    ICart cartThree = new Cart(itemsThree); 
    
    IMarketState mState = new MarketState();
    
    actRule.isAcceptable(mState, messageTwo, current, cartThree);
    
    assertTrue(mState.getAcceptable()); 
    
  }
  
  // test that not acceptable for agent to bid on item not in market. 
  @Test
  public void testOneShotActivityThree() {
    
    Map<ICart, Double> cartsOne = new HashMap<ICart, Double>(); 
    Map<ICart, Double> cartsTwo = new HashMap<ICart, Double>(); 
    
    List<IItem> items = new LinkedList<IItem>(); 
    List<IItem> itemsTwo = new LinkedList<IItem>();
    List<IItem> itemsThree = new LinkedList<IItem>(); 
    
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
    
    List<ITradeMessage> current = new LinkedList<ITradeMessage>(); 
    current.add(messageOne); 
    
    IActivityRule actRule = new SimpleOneShotActivity();
    
    itemsThree.add(new Item("a")); 
    
    ICart cartThree = new Cart(itemsThree); 
    
    IMarketState mState = new MarketState();
    
    actRule.isAcceptable(mState, messageTwo, current, cartThree);
    
    assertTrue(!mState.getAcceptable()); 
    
  }
  
  // test that not acceptable for agent to bid more than one of something. 
  @Test
  public void testOneShotActivityFour() {
    
    Map<ICart, Double> cartsOne = new HashMap<ICart, Double>(); 
    Map<ICart, Double> cartsTwo = new HashMap<ICart, Double>(); 
    
    List<IItem> items = new LinkedList<IItem>(); 
    List<IItem> itemsTwo = new LinkedList<IItem>();
    List<IItem> itemsThree = new LinkedList<IItem>(); 
    List<IItem> itemsFour = new LinkedList<IItem>(); 
    
    
    items.add(new Item("a"));
    itemsTwo.add(new Item("b"));
    itemsThree.add(new Item("a")); 
    itemsThree.add(new Item("b"));
    // not acceptable because item has count larger than one. 
    itemsFour.add(new Item("b", 2)); 
    
    
    cartsOne.put(new Cart(items), 1.0); 
    cartsOne.put(new Cart(itemsTwo), 2.0);
    IBidBundle bb = new OneSidedBidBundle(cartsOne); 
    ITradeMessage messageOne = new TradeMessage(0, 0, 0, bb); 
    
    cartsTwo.put(new Cart(items), 2.0); 
    cartsTwo.put(new Cart(itemsFour), 1.0); 
    IBidBundle bbTwo = new OneSidedBidBundle(cartsTwo); 
    ITradeMessage messageTwo = new TradeMessage(0, 1, 0, bbTwo);
    
    List<ITradeMessage> current = new LinkedList<ITradeMessage>(); 
    current.add(messageOne); 
    
    IActivityRule actRule = new SimpleOneShotActivity();
    
    ICart cartThree = new Cart(itemsThree); 
    
    IMarketState mState = new MarketState();
    
    actRule.isAcceptable(mState, messageTwo, current, cartThree);
    
    assertTrue(!mState.getAcceptable()); 
    
  }
  
  // test that not acceptable for agent to bid more than one of something. 
  @Test
  public void testOneShotActivityFive() {
    
    Map<ICart, Double> cartsOne = new HashMap<ICart, Double>(); 
    Map<ICart, Double> cartsTwo = new HashMap<ICart, Double>(); 
    
    List<IItem> items = new LinkedList<IItem>(); 
    List<IItem> itemsTwo = new LinkedList<IItem>();
    List<IItem> itemsThree = new LinkedList<IItem>(); 
    
    
    items.add(new Item("a"));
    itemsTwo.add(new Item("b"));
    itemsThree.add(new Item("a")); 
    itemsThree.add(new Item("b")); 
    
    
    cartsOne.put(new Cart(items), 1.0); 
    cartsOne.put(new Cart(itemsTwo), 2.0);
    IBidBundle bb = new OneSidedBidBundle(cartsOne); 
    ITradeMessage messageOne = new TradeMessage(0, 0, 0, bb); 
    
    cartsTwo.put(new Cart(items), 2.0); 
    // not acceptable because cart has more than one item
    cartsTwo.put(new Cart(itemsThree), 1.0); 
    IBidBundle bbTwo = new OneSidedBidBundle(cartsTwo); 
    ITradeMessage messageTwo = new TradeMessage(0, 1, 0, bbTwo);
    
    List<ITradeMessage> current = new LinkedList<ITradeMessage>(); 
    current.add(messageOne); 
    
    IActivityRule actRule = new SimpleOneShotActivity();
    
    ICart cartThree = new Cart(itemsThree); 
    
    IMarketState mState = new MarketState();
    
    actRule.isAcceptable(mState, messageTwo, current, cartThree);
    
    assertTrue(!mState.getAcceptable()); 
    
  }
  
}
