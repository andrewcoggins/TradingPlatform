package brown.user.main.library;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.auction.endowment.distribution.library.IndependentEndowmentDist;
import brown.auction.value.generator.library.NormalValGenerator;
import brown.platform.item.ICart;
import brown.user.main.IEndowmentConfig;

public class EndowmentConfigTest {
  
  @Test
  public void testEndowmentConfigOne() throws NoSuchMethodException, SecurityException {
    
    Constructor<?> distCons = IndependentEndowmentDist.class.getConstructor(ICart.class, List.class);
    List<String> tNames = new LinkedList<String>();
    tNames.add("test"); 
    Constructor<?> gCons = NormalValGenerator.class.getConstructor(List.class); 
    List<Double> params = new LinkedList<Double>(); 
    params.add(0.0); 
    params.add(1.0);  
    
    List<Constructor<?>> genList = new LinkedList<Constructor<?>>(); 
    List<List<Double>> paramList = new LinkedList<List<Double>>(); 

    genList.add(gCons); 
    paramList.add(params); 
    IEndowmentConfig tConfig = new EndowmentConfig(tNames, distCons, genList, paramList);
    
    assertEquals(tConfig.getItemNames(), tNames); 
    assertEquals(tConfig.getDistribution(), distCons);
    assertEquals(tConfig.getGeneratorConstructors(), genList);
    assertEquals(tConfig.getGeneratorParams(), paramList);
  }
  
}
