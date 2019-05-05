package brown.platform.item.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.platform.item.IItem;

public class SingleItemTest {

  @Test 
  public void testSingleItem() {
    
    IItem a = new SingleItem("a"); 
    IItem b = new SingleItem("b"); 
    
    assertTrue(a.getItemCount() == 1); 
    assertTrue(b.getItemCount() == 1); 
    assertEquals(a.getName(), "a"); 
    assertEquals(b.getName(), "b");
  }
}
