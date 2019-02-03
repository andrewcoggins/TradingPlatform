package brown.user.main;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Constructor;
import org.junit.Test;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.user.main.ITradeableConfig;
import brown.user.main.library.TradeableConfig;

public class TradeableConfigTest {
  
  @Test
  public void testTradeableConfigOne() throws NoSuchMethodException, SecurityException {
    
    Constructor<?> tTypeCons = SimpleTradeable.class.getConstructor(Integer.class); 
    ITradeableConfig tConfig = new TradeableConfig("trade", tTypeCons, 10);
    
    assertEquals(tConfig.getTradeableName(), "trade"); 
    assertEquals(tConfig.getTType(), tTypeCons); 
    assertEquals(tConfig.getNumTradeables(), new Integer(10)); 
  }
  
  public static void main(String[] args) throws NoSuchMethodException, SecurityException {
    TradeableConfigTest t = new TradeableConfigTest(); 
    t.testTradeableConfigOne(); 
  }
}