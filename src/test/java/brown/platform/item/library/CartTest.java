package brown.platform.item.library;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.platform.item.ICart;
import brown.platform.item.IItem;

public class CartTest {

  @Test 
  public void testCart() {
    List<IItem> items = new LinkedList<IItem>(); 
    items.add(new SingleItem("a")); 
    items.add(new SingleItem("b")); 
    
    ICart cart = new Cart(items); 
    assertEquals(cart.getItems(), items); 
  }
  
  @Test
  public void testCartTwo() {
    List<IItem> items = new LinkedList<IItem>(); 
    items.add(new MultiItem("a", 4)); 
    items.add(new MultiItem("b", 1)); 
    
    ICart cart = new Cart(items); 
    assertEquals(cart.getItems(), items); 
    
  }
}
