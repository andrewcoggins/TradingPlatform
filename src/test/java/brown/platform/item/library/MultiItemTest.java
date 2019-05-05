package brown.platform.item.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.platform.item.IItem;

public class MultiItemTest {

  @Test
  public void testMultiItem() {
    
    IItem a = new MultiItem("a", 5); 
    IItem b = new MultiItem("b", 6); 
    
    assertTrue(a.getItemCount() == 5); 
    assertTrue(b.getItemCount() == 6); 
    assertEquals(a.getName(), "a"); 
    assertEquals(b.getName(), "b");
  }
}
