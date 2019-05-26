package brown.platform.accounting.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.platform.item.IItem;
import brown.platform.item.library.Item;

/**
 * Test for Transaction. 
 * @author andrew
 *
 */
public class TransactionTest {
  
  @Test
  public void testTransactionr() {

    IItem anItem = new Item("a");
    Transaction anOrder = new Transaction(0, 100.0, anItem);
    assertTrue(anOrder.TO == 0);
    assertTrue(anOrder.FROM == -1);
    assertTrue(anOrder.PRICE == 100.0);
    assertEquals(anOrder.ITEM, anItem);

  }

  @Test
  public void testTransactionTwo() {

    IItem anItem = new Item("c", 3);
    Transaction anOrder = new Transaction(10, 11, 10.0, anItem);
    assertTrue(anOrder.TO == 10);
    assertTrue(anOrder.FROM == 11);
    assertTrue(anOrder.PRICE == 10.0);
    assertEquals(anOrder.ITEM, anItem);
  }
  
}