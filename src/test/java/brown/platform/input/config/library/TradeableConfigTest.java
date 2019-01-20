package brown.platform.input.config.library;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.auction.value.distribution.library.AdditiveValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.generator.library.NormalValGenerator;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.platform.input.config.ITradeableConfig;

public class TradeableConfigTest {
  
  @Test
  public void testTradeableConfigOne() throws NoSuchMethodException, SecurityException {
    
    Constructor<?> distCons = AdditiveValuationDistribution.class.getConstructor(IValuationGenerator.class, Set.class);
    Constructor<?> tTypeCons = SimpleTradeable.class.getConstructor(Integer.class); 
    Constructor<?> gCons = NormalValGenerator.class.getConstructor(Double.class, Double.class); 
    List<Double> params = new LinkedList<Double>(); 
    params.add(0.0); 
    params.add(1.0);  
    Map<Constructor<?>, List<Double>> gMap = new HashMap<Constructor<?>, List<Double>>(); 
    gMap.put(gCons, params); 
    ITradeableConfig tConfig = new TradeableConfig("trade", tTypeCons, 10, 
        distCons, gMap);
    
    assertEquals(tConfig.getTradeableName(), "trade"); 
    assertEquals(tConfig.getTType(), tTypeCons); 
    assertEquals(tConfig.getNumTradeables(), new Integer(10)); 
    assertEquals(tConfig.getValDistribution(), distCons);
    assertEquals(tConfig.getGenerator(), gMap);
  }
 
  
  public static void main(String[] args) throws NoSuchMethodException, SecurityException {
    TradeableConfigTest t = new TradeableConfigTest(); 
    t.testTradeableConfigOne(); 
  }
}