package brown.user.main.library;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Constructor;
import org.junit.Test;

import brown.platform.tradeable.library.Tradeable;
import brown.user.main.ITradeableConfig;
import brown.user.main.library.TradeableConfig;

public class TradeableConfigTest {
  
  @Test
  public void testTradeableConfigOne() throws NoSuchMethodException, SecurityException {
    
    Constructor<?> tTypeCons = Tradeable.class.getConstructor(Integer.class); 
    ITradeableConfig tConfig = new TradeableConfig("trade", tTypeCons, 10);
    
    assertEquals(tConfig.getTradeableName(), "trade"); 
    assertEquals(tConfig.getTType(), tTypeCons); 
    assertEquals(tConfig.getNumTradeables(), new Integer(10)); 
  }
  
}
