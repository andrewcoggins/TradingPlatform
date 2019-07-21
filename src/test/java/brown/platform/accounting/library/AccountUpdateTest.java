package brown.platform.accounting.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;

public class AccountUpdateTest {

  @Test
  public void testOrder() {
    List<IItem> items = new LinkedList<IItem>();
    IItem anItem = new Item("a");
    items.add(anItem); 
    ICart cart = new Cart(items);
    AccountUpdate anOrder = new AccountUpdate(0, 100.0, cart);
    assertTrue(anOrder.getTo() == 0);
    assertTrue(anOrder.getFrom() == -1);
    assertTrue(anOrder.getCost() == 100.0);
    assertEquals(anOrder.getCart(), cart);
  }

  @Test
  public void testOrderTwo() {
    List<IItem> items = new LinkedList<IItem>();
    IItem anItem = new Item("c", 3);
    items.add(anItem); 
    ICart cart = new Cart(items);
    AccountUpdate anOrder = new AccountUpdate(10, 11, 10.0, cart);
    assertTrue(anOrder.getTo() == 10);
    assertTrue(anOrder.getFrom() == 11);
    assertTrue(anOrder.getCost() == 10.0);
    assertEquals(anOrder.getCart(), cart);
  }

  @Test
  public void testOrderThree() {
    List<IItem> items = new LinkedList<IItem>();
    IItem anItem = new Item("d", 3);
    items.add(anItem); 
    ICart cart = new Cart(items);
    AccountUpdate anOrder = new AccountUpdate(0, 1, 100.0, cart);
    Transaction aTransaction = new Transaction(0, 1, 100.0, cart);
    assertEquals(anOrder.toTransaction(), aTransaction);
  }

}
