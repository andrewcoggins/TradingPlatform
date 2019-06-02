package brown.platform.item.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.platform.item.IItem;

public class SingleItemTest {

  @Test 
  public void testSingleItem() {
    
    IItem a = new Item("a"); 
    IItem b = new Item("b"); 
    
    IItem aTwo = new Item("a"); 
    
    assertTrue(a.getItemCount() == 1); 
    assertTrue(b.getItemCount() == 1); 
    assertEquals(a.getName(), "a"); 
    assertEquals(b.getName(), "b");
    
    a.addItemCount(aTwo.getItemCount()); 
    assertTrue(a.getItemCount() == 2); 
  }
}
