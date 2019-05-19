package brown.user.main.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import brown.user.main.IItemConfig;

public class TradeableConfigTest {
  
  @Test
  public void testTradeableConfigOne() throws NoSuchMethodException, SecurityException {

    IItemConfig tConfig = new ItemConfig("trade", 10);
    
    assertEquals(tConfig.getTradeableName(), "trade"); 
    assertEquals(tConfig.getNumTradeables(), new Integer(10)); 
  }
  
}
