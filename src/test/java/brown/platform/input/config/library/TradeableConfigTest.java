package brown.platform.input.config.library;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Test;
import brown.auction.value.distribution.library.AdditiveValuationDistribution;
import brown.auction.value.generator.library.NormalValGenerator;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.TradeableType;
import brown.platform.input.config.ITradeableConfig;

public class TradeableConfigTest {
  
  @Test
  public void testTradeableConfigOne() {
    ITradeableConfig tConfig = new TradeableConfig("trade", TradeableType.Simple, 10, 
        new AdditiveValuationDistribution(new NormalValGenerator(0.0, 1.0), new HashSet<ITradeable>()));
    
    assertEquals(tConfig.getTradeableName(), "trade"); 
    assertEquals(tConfig.getTType(), TradeableType.Simple); 
    assertEquals(tConfig.getNumTradeables(), new Integer(10)); 
    assertEquals(tConfig.getValDistribution(),
        new AdditiveValuationDistribution(new NormalValGenerator(0.0, 1.0), new HashSet<ITradeable>()));  
  }
 
  
  public static void main(String[] args) {
    TradeableConfigTest t = new TradeableConfigTest(); 
    t.testTradeableConfigOne(); 
  }
}