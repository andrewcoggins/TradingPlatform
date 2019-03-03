package brown.platform.accounting.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.platform.tradeable.library.SimpleTradeable;

public class OrderTest {
  
  @Test
  public void testOrder() {
    
    SimpleTradeable aGood = new SimpleTradeable(0);
    AccountUpdate anOrder = new AccountUpdate(0, 1, 100.0, 1, aGood); 
    assertTrue(anOrder.TO == 0); 
    assertTrue(anOrder.FROM == 1); 
    assertTrue(anOrder.PRICE == 100.0); 
    assertTrue(anOrder.QUANTITY == 1); 
    assertEquals(anOrder.GOOD, aGood); 
    
    
  }
  
  @Test
  public void testOrderTwo() {
    
    SimpleTradeable aGood = new SimpleTradeable(2);
    AccountUpdate anOrder = new AccountUpdate(10, 9, 10.0, 4, aGood); 
    assertTrue(anOrder.TO == 10); 
    assertTrue(anOrder.FROM == 9); 
    assertTrue(anOrder.PRICE == 10.0); 
    assertTrue(anOrder.QUANTITY == 4); 
    assertEquals(anOrder.GOOD, aGood); 
    
    
  }
  
  @Test
  public void testOrderThree() {
    
    SimpleTradeable aGood = new SimpleTradeable(0);
    AccountUpdate anOrder = new AccountUpdate(0, 1, 100.0, 1, aGood); 
    AccountUpdate anOrder2 = new AccountUpdate(0, 1, 90.0, 1, aGood); 
    assertEquals(anOrder.updatePrice(90.0), anOrder2); 
    AccountUpdate anOrder3 = new AccountUpdate(0, 1, 100.0, 2, aGood); 
    anOrder.updateQuantity(2);
    assertEquals(anOrder, anOrder3); 
    anOrder.updateQuantity(1);
    AccountUpdate anOrder4 = new AccountUpdate(2, 1, 100.0, 1, aGood); 
    assertEquals(anOrder.updateToAgent(2), anOrder4); 
    AccountUpdate anOrder5 = new AccountUpdate(0, 4, 100.0, 1, aGood);
    assertEquals(anOrder.updateFromAgent(4), anOrder5); 
    
    Transaction aTransaction = new Transaction(0, 1, 100.0, 1, aGood); 
    assertEquals(anOrder.toTransaction(), aTransaction); 
    
    
    
  }
  
}