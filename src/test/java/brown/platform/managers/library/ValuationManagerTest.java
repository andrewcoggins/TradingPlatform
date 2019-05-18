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
import brown.auction.value.valuation.ISpecificValuation;
import brown.auction.value.valuation.library.AdditiveValuation;
import brown.platform.item.ISingleItem;
import brown.platform.item.library.SingleItem;
import brown.platform.managers.IValuationManager;
import brown.platform.tradeable.ITradeable;
import brown.platform.tradeable.library.Tradeable;

public class ValuationManagerTest {
  
  @Test
  public void testCreateValuation() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
  IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Map<String, List<ITradeable>> tradeables = new HashMap<String, List<ITradeable>>(); 
    List<ITradeable> tList = new LinkedList<ITradeable>(); 
    tList.add(new Tradeable(0, "default")); 
    tradeables.put("default", tList); 
    
    Class distClass = Class.forName("brown.auction.value.distribution.library.AdditiveValuationDistribution");
    Constructor<?> distCons = distClass.getConstructor(Map.class, List.class); 
    
    Map<Constructor<?>, List<Double>> generatorMap = new HashMap<Constructor<?>, List<Double>>();
    Class genClass = Class.forName("brown.auction.value.generator.library.NormalValGenerator"); 
    List<Double> genParams = new LinkedList<Double>(); 
    genParams.add(0.0); 
    genParams.add(1.0); 
    
    Constructor<?> genCons = genClass.getConstructor(List.class); 
    generatorMap.put(genCons, genParams); 
    
    IValuationManager valManager = new ValuationManager();
    valManager.createValuation(distCons, generatorMap, tradeables);
    
    List<IValuationGenerator> expectedGenList = new LinkedList<IValuationGenerator>(); 
    IValuationGenerator expectedGen = (IValuationGenerator) genCons.newInstance(genParams); 
    expectedGenList.add(expectedGen); 
    IValuationDistribution expected = new AdditiveValuationDistribution(tradeables, expectedGenList); 
    
    assertEquals(valManager.getDistribution(), expected); 
  }
  
  @Test
  public void testAgentValuation() {
    
    IValuationManager vManager = new ValuationManager(); 
    List<String> tradeableNames = new LinkedList<String>(); 
    tradeableNames.add("default"); 
    Map<ISingleItem, Double> valueParams = new HashMap<ISingleItem, Double>(); 
    valueParams.put(new SingleItem("default"), 1.0); 
    ISpecificValuation agentValuation = new AdditiveValuation(valueParams); 
    vManager.addAgentValuation(1, tradeableNames, agentValuation);
    
    assertEquals(vManager.getAgentValuation(1), agentValuation); 
      
  }
  
}
