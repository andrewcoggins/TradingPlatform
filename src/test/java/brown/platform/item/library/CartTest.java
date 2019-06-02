package brown.platform.item.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.platform.item.ICart;
import brown.platform.item.IItem;

public class CartTest {

  @Test 
  public void testCart() {
    List<IItem> items = new LinkedList<IItem>(); 
    items.add(new Item("a")); 
    items.add(new Item("b")); 
    
    ICart cart = new Cart(items); 
    assertEquals(cart.getItems(), items); 
    
    assertEquals(cart.getItemByName("a"), new Item("a")); 
    assertTrue(cart.containsItem("a")); 
    assertTrue(!cart.containsItem("c")); 
    
    System.out.println(cart.toString()); 
    
    cart.addToCart(new Item("a"));
    
    System.out.println(cart.toString()); 
    assertEquals(cart.getItemByName("a"), new Item("a", 2)); 
    
    List<IItem> newItems = new LinkedList<IItem>(); 
    newItems.add(new Item("a", 2));
    newItems.add(new Item("b")); 
    
    assertEquals(cart.getItems(), newItems); 
  }
  
  @Test
  public void testCartTwo() {
    List<IItem> items = new LinkedList<IItem>(); 
    items.add(new Item("a", 4)); 
    items.add(new Item("b", 1)); 
    
    ICart cart = new Cart(items); 
    assertEquals(cart.getItems(), items); 
    
    assertEquals(cart.getItemByName("a"), new Item("a", 4)); 
    assertTrue(cart.containsItem("a")); 
    assertTrue(!cart.containsItem("c")); 
    
    System.out.println(cart.toString()); 
    
    cart.addToCart(new Item("a"));
    cart.addToCart(new Item("b", 2));
    cart.addToCart(new Item("c", 4));
    System.out.println(cart.toString()); 
    assertEquals(cart.getItemByName("a"), new Item("a", 5)); 
    
    List<IItem> newItems = new LinkedList<IItem>(); 
    newItems.add(new Item("a", 5));
    newItems.add(new Item("b", 3)); 
    newItems.add(new Item("c", 4)); 
    
    assertEquals(cart.getItems(), newItems); 
    
    cart.removeFromCart(new Item("c", 1));
    
    assertEquals(cart.getItemByName("c"), new Item("c", 3)); 
    
    newItems.remove(2); 
    
    newItems.add(new Item("c", 3)); 
    
    assertEquals(cart.getItems(), newItems); 
    
  }
}
