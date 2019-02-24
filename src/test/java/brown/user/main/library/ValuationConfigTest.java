package brown.user.main.library;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.value.distribution.library.AdditiveValuationDistribution;
import brown.auction.value.generator.library.NormalValGenerator;
import brown.user.main.IValuationConfig;
import brown.user.main.library.ValuationConfig;

public class ValuationConfigTest {
  
  @Test
  public void testValuationConfigOne() throws NoSuchMethodException, SecurityException {
    
    Constructor<?> distCons = AdditiveValuationDistribution.class.getConstructor(Map.class, List.class);
    List<String> tNames = new LinkedList<String>();
    tNames.add("test"); 
    Constructor<?> gCons = NormalValGenerator.class.getConstructor(List.class); 
    List<Double> params = new LinkedList<Double>(); 
    params.add(0.0); 
    params.add(1.0);  
    Map<Constructor<?>, List<Double>> gMap = new HashMap<Constructor<?>, List<Double>>(); 
    gMap.put(gCons, params); 
    IValuationConfig tConfig = new ValuationConfig(tNames, distCons, gMap);
    
    assertEquals(tConfig.getTradeableNames(), tNames); 
    assertEquals(tConfig.getValDistribution(), distCons);
    assertEquals(tConfig.getGenerators(), gMap);
  }
  
}
