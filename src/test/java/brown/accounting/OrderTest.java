package brown.accounting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.market.marketstate.library.Order;
import brown.tradeable.library.MultiTradeable;

/**
 * tests the Order class. 
 * C
 * @author andrew
 *
 */
public class OrderTest {
  
  @Test
  public void testOrder() {
    Order anOrder = new Order(0, 1, 100.0, 5, new MultiTradeable(0)); 
    assertTrue(anOrder.TO == 0); 
    assertTrue(anOrder.FROM == 1); 
    assertTrue(anOrder.PRICE == 100.0); 
    assertTrue(anOrder.QUANTITY == 5);
    assertTrue(anOrder.GOOD.equals(new MultiTradeable(0)));
    anOrder.updateQuantity(6);
    assertTrue(anOrder.QUANTITY == 6);
    assertEquals(anOrder.toTransaction(), new Transaction(0, 1, 100.0, 6, new MultiTradeable(0)));
    assertEquals(anOrder.updatePrice(90.0), new Order(0, 1, 90.0, 6, new MultiTradeable(0)));
    assertEquals(anOrder.updateToAgent(5), new Order(5, 1, 100.0, 6, new MultiTradeable(0)));
  }
}
