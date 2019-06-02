package brown.user.main.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.user.main.IItemConfig;

public class ItemConfigTest {
  
  @Test
  public void testTradeableConfigOne() throws NoSuchMethodException, SecurityException {

    IItemConfig tConfig = new ItemConfig("trade", 10);
    
    assertEquals(tConfig.getItemName(), "trade"); 
    assertTrue(tConfig.getNumItems() == 10); 
  }
  
}
