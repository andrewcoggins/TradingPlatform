package brown.platform.item.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.platform.item.IItem;

public class MultiItemTest {

  @Test
  public void testMultiItem() {
    
    IItem a = new Item("a", 5); 
    IItem b = new Item("b", 6); 
    
    IItem aTwo = new Item("a", 7); 
    
    
    assertTrue(a.getItemCount() == 5); 
    assertTrue(b.getItemCount() == 6); 
    assertEquals(a.getName(), "a"); 
    assertEquals(b.getName(), "b");
    
    a.addItemCount(aTwo.getItemCount());
    
    assertTrue(a.getItemCount() == 12); 
    
    a.removeItemCount(4);
    
    assertTrue(a.getItemCount() == 8); 
  }
}
