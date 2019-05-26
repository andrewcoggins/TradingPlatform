package brown.platform.managers.library;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.distribution.library.AdditiveValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.valuation.IGeneralValuation;
import brown.auction.value.valuation.ISpecificValuation;
import brown.auction.value.valuation.library.AdditiveValuation;
import brown.auction.value.valuation.library.GeneralValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;
import brown.platform.managers.IValuationManager;

public class ValuationManagerTest {
  
  @Test
  public void testCreateValuation() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
  IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    
    List<IItem> items = new LinkedList<IItem>(); 
    items.add(new Item("default")); 
    
    ICart aCart = new Cart(items); 
    
    Class distClass = Class.forName("brown.auction.value.distribution.library.AdditiveValuationDistribution");
    Constructor<?> distCons = distClass.getConstructor(ICart.class, List.class); 
    
    Map<Constructor<?>, List<Double>> generatorMap = new HashMap<Constructor<?>, List<Double>>();
    Class genClass = Class.forName("brown.auction.value.generator.library.NormalValGenerator"); 
    List<Double> genParams = new LinkedList<Double>(); 
    genParams.add(0.0); 
    genParams.add(1.0); 
    
    Constructor<?> genCons = genClass.getConstructor(List.class); 
    generatorMap.put(genCons, genParams); 
    
    IValuationManager valManager = new ValuationManager();
    valManager.createValuation(distCons, generatorMap, aCart);
    
    List<IValuationGenerator> expectedGenList = new LinkedList<IValuationGenerator>(); 
    IValuationGenerator expectedGen = (IValuationGenerator) genCons.newInstance(genParams); 
    expectedGenList.add(expectedGen); 
    IValuationDistribution expected = new AdditiveValuationDistribution(aCart, expectedGenList); 
    
    assertEquals(valManager.getDistribution().get(0), expected); 
  }
  
  @Test
  public void testAgentValuation() {
    
    IValuationManager vManager = new ValuationManager(); 
    List<String> tradeableNames = new LinkedList<String>(); 
    tradeableNames.add("default"); 
    Map<IItem, Double> valueParams = new HashMap<IItem, Double>(); 
    valueParams.put(new Item("default"), 1.0); 
    ISpecificValuation agentValuation = new AdditiveValuation(valueParams); 
    Map<List<IItem>, ISpecificValuation> specific = new HashMap<List<IItem>, ISpecificValuation>(); 
    specific.put(new LinkedList<IItem>(valueParams.keySet()), agentValuation); 
    IGeneralValuation gValuation = new GeneralValuation(specific); 
    vManager.addAgentValuation(1, gValuation);
    
    assertEquals(vManager.getAgentValuation(1), gValuation); 
      
  }
  
}
