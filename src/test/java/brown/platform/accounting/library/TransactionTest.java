package brown.platform.accounting.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;

/**
 * Test for Transaction. 
 * @author andrew
 *
 */
public class TransactionTest {
  
  @Test
  public void testTransactionr() {
    List<IItem> items = new LinkedList<IItem>(); 
    IItem anItem = new Item("a");
    items.add(anItem); 
    Transaction anOrder = new Transaction(0, 100.0, new Cart(items));
    assertTrue(anOrder.getTo() == 0);
    assertTrue(anOrder.getFrom() == -1);
    assertTrue(anOrder.getCost() == 100.0);
    assertEquals(anOrder.getCart(), new Cart(items));
  }

  @Test
  public void testTransactionTwo() {
    List<IItem> items = new LinkedList<IItem>(); 
    IItem anItem = new Item("c", 3);
    items.add(anItem); 
    Transaction anOrder = new Transaction(10, 11, 10.0, new Cart(items));
    assertTrue(anOrder.getTo() == 10);
    assertTrue(anOrder.getFrom() == 11);
    assertTrue(anOrder.getCost() == 10.0);
    assertEquals(anOrder.getCart(), new Cart(items));
  }
  
}