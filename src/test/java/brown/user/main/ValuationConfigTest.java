package brown.user.main;

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

public class ValuationConfigTest {
  
  @Test
  public void testValuationConfigOne() throws NoSuchMethodException, SecurityException {
    
    Constructor<?> distCons = AdditiveValuationDistribution.class.getConstructor(IValuationGenerator.class, Set.class);
    List<String> tNames = new LinkedList<String>();
    tNames.add("test"); 
    Constructor<?> gCons = NormalValGenerator.class.getConstructor(Double.class, Double.class); 
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
  
  public static void main(String[] args) throws NoSuchMethodException, SecurityException {
    ValuationConfigTest v = new ValuationConfigTest(); 
    v.testValuationConfigOne();
  }
}
