package brown.platform.accounting.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.platform.tradeable.library.SimpleTradeable;

public class OrderTest {
  
  @Test
  public void testOrder() {
    
    SimpleTradeable aGood = new SimpleTradeable(0);
    Order anOrder = new Order(0, 1, 100.0, 1, aGood); 
    assertTrue(anOrder.TO == 0); 
    assertTrue(anOrder.FROM == 1); 
    assertTrue(anOrder.PRICE == 100.0); 
    assertTrue(anOrder.QUANTITY == 1); 
    assertEquals(anOrder.GOOD, aGood); 
    
    
  }
  
  @Test
  public void testOrderTwo() {
    
    SimpleTradeable aGood = new SimpleTradeable(2);
    Order anOrder = new Order(10, 9, 10.0, 4, aGood); 
    assertTrue(anOrder.TO == 10); 
    assertTrue(anOrder.FROM == 9); 
    assertTrue(anOrder.PRICE == 10.0); 
    assertTrue(anOrder.QUANTITY == 4); 
    assertEquals(anOrder.GOOD, aGood); 
    
    
  }
  
  @Test
  public void testOrderThree() {
    
    SimpleTradeable aGood = new SimpleTradeable(0);
    Order anOrder = new Order(0, 1, 100.0, 1, aGood); 
    Order anOrder2 = new Order(0, 1, 90.0, 1, aGood); 
    assertEquals(anOrder.updatePrice(90.0), anOrder2); 
    Order anOrder3 = new Order(0, 1, 100.0, 2, aGood); 
    anOrder.updateQuantity(2);
    assertEquals(anOrder, anOrder3); 
    anOrder.updateQuantity(1);
    Order anOrder4 = new Order(2, 1, 100.0, 1, aGood); 
    assertEquals(anOrder.updateToAgent(2), anOrder4); 
    Order anOrder5 = new Order(0, 4, 100.0, 1, aGood);
    assertEquals(anOrder.updateFromAgent(4), anOrder5); 
    
    Transaction aTransaction = new Transaction(0, 1, 100.0, 1, aGood); 
    assertEquals(anOrder.toTransaction(), aTransaction); 
    
    
    
  }
  
  public static void main(String[] args) {
    OrderTest t = new OrderTest(); 
    t.testOrder();
    t.testOrderTwo();
    t.testOrderThree();
  }
}