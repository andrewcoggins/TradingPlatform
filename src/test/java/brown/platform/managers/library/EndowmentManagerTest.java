package brown.platform.managers.library;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.endowment.IEndowment;
import brown.auction.endowment.distribution.IEndowmentDistribution;
import brown.auction.endowment.distribution.library.IndependentEndowmentDist;
import brown.auction.endowment.library.Endowment;
import brown.auction.value.generator.IValuationGenerator;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;
import brown.platform.managers.IEndowmentManager;

public class EndowmentManagerTest {
  
  @Test
  public void testCreateEndowment() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
  IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    
    List<IItem> items = new LinkedList<IItem>(); 
    items.add(new Item("default")); 
    
    ICart aCart = new Cart(items); 
    
    Class distClass = Class.forName("brown.auction.endowment.distribution.library.IndependentEndowmentDist");
    Constructor<?> distCons = distClass.getConstructor(ICart.class, List.class); 
    
    Map<Constructor<?>, List<Double>> generatorMap = new HashMap<Constructor<?>, List<Double>>();
    Class genClass = Class.forName("brown.auction.value.generator.library.NormalValGenerator"); 
    List<Double> genParams = new LinkedList<Double>(); 
    genParams.add(0.0); 
    genParams.add(1.0); 
    
    Constructor<?> genCons = genClass.getConstructor(List.class); 
    generatorMap.put(genCons, genParams); 
    
    IEndowmentManager valManager = new EndowmentManager();
    valManager.createEndowment(distCons, generatorMap, aCart);
    
    List<IValuationGenerator> expectedGenList = new LinkedList<IValuationGenerator>(); 
    IValuationGenerator expectedGen = (IValuationGenerator) genCons.newInstance(genParams); 
    expectedGenList.add(expectedGen); 
    IEndowmentDistribution expected = new IndependentEndowmentDist(aCart, expectedGenList); 
    
    assertEquals(valManager.getDistribution().get(0), expected); 
  }
  
//  @Test
//  public void testAgentEndowment() {
//    
//    IEndowmentManager vManager = new EndowmentManager(); 
//    List<String> tradeableNames = new LinkedList<String>(); 
//    tradeableNames.add("default"); 
//    Map<IItem, Double> valueParams = new HashMap<IItem, Double>(); 
//    valueParams.put(new Item("default"), 1.0); 
//    IEndowment agentEndowment = new Endowment(new Cart, 100.0); 
//    Map<List<IItem>, IEndowment>  = new HashMap<List<IItem>, IEndowment>(); 
//    .put(new LinkedList<IItem>(valueParams.keySet()), agentEndowment); 
//    IEndowment gEndowment = new Endowment(); 
//    vManager.addAgentEndowment(1, gEndowment);
//    
//    assertEquals(vManager.getAgentEndowment(1), gEndowment); 
//      
//  }
  
}