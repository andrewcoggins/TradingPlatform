package brown.platform.accounting.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.platform.accounting.IAccount;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;

/**
 * Test for account class.
 * @author andrew
 *
 */
public class AccountTest {
  
  @Test
  public void testAccountInit() {
    
    List<IItem> someItems = new LinkedList<IItem>(); 
    someItems.add(new Item("default", 2)); 
    
    ICart aCart = new Cart(someItems); 
    
    IAccount a = new Account(100, 90.0, aCart);
    
    assertTrue(a.getID() == 100); 
    
    assertTrue(a.getMoney() == 90.0); 
    
    assertEquals(a.getAllGoods(), aCart); 
    
    assertEquals(a.getGoods("default"), new Item("default", 2)); 
  }
  
  
  @Test
  public void testAccount() {
    
    List<IItem> someItems = new LinkedList<IItem>(); 
    someItems.add(new Item("default", 2)); 
    
    ICart aCart = new Cart(someItems); 
    
    IAccount a = new Account(5, 100.0, aCart); 
    
    // test money
    
    a.addMoney(50.0);
    
    assertTrue(a.getMoney() == 150.0);
    
    a.removeMoney(90.0);
    
    assertTrue(a.getMoney() == 60.0);
    
    // test tradeables
    
    List<IItem> moreItems = new LinkedList<IItem>(); 
    
    moreItems.add(new Item("default", 2)); 
    
    
    a.addTradeables(new Item("default", 3));
    
    
    assertEquals(a.getGoods("default"), new Item("default", 5)); 
    
    
    List<IItem> allItems = new LinkedList<IItem>(); 
    allItems.add(new Item("default", 5)); 
    
    assertEquals(a.getAllGoods(), new Cart(allItems)); 
    
    a.addTradeables(new Item("b", 2));
    
    allItems.add(new Item("b", 2)); 
    
    assertEquals(a.getAllGoods(), new Cart(allItems)); 
    
//    assertEquals(a.getAllGoods(), tradeables); 
//    
//    List<ITradeable> evenMoreTradeables = new LinkedList<ITradeable>(); 
//    
//    evenMoreTradeables.add(new Tradeable(3, "default")); 
//    
//    a.addTradeables("other", evenMoreTradeables);
//    
//    assertEquals(a.getGoods("other"), evenMoreTradeables); 
//    
//    tradeables.put("other", evenMoreTradeables);
//    
//    assertEquals(a.getAllGoods(), tradeables); 
//    
//    a.removeTradeables("other", evenMoreTradeables);
//    
//    tradeables.put("other", new LinkedList<ITradeable>());
//    
//    assertEquals(a.getAllGoods(), tradeables);
//    
//    // test clear function
//    
//    a.clear();
//    
//    assertTrue(a.getMoney() == 0.0); 
//    
//    assertEquals(a.getAllGoods(), new HashMap<String, List<ITradeable>>()); 
 
  }
  
  
}