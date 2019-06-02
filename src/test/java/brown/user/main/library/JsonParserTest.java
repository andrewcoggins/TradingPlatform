package brown.user.main.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import brown.auction.rules.activity.onesided.SimpleOneShotActivity;
import brown.auction.rules.allocation.onesided.SimpleHighestPriceAllocation;
import brown.auction.rules.innerir.onesided.NoInnerIR;
import brown.auction.rules.ir.onesided.AnonymousPolicy;
import brown.auction.rules.payment.onesided.SecondPricePayment;
import brown.auction.rules.query.onesided.SimpleOneSidedQuery;
import brown.auction.rules.termination.onesided.OneShotTermination;
import brown.platform.item.ICart;
import brown.platform.market.library.FlexibleRules;
import brown.user.main.IEndowmentConfig;
import brown.user.main.IItemConfig;
import brown.user.main.IJsonParser;
import brown.user.main.IMarketConfig;
import brown.user.main.ISimulationConfig;
import brown.user.main.IValuationConfig;

public class JsonParserTest {

  @Test
  public void testJSONParse() throws FileNotFoundException,
      ClassNotFoundException, NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, IOException, ParseException {
    IJsonParser testParser = new JsonParser();
    List<ISimulationConfig> simulationConfigs = testParser.parseJSON("input_configs/test_input.json");
    assertTrue(simulationConfigs.size() == 2); 
    
    ISimulationConfig firstSimulationConfig = simulationConfigs.get(0); 
    ISimulationConfig secondSimulationConfig = simulationConfigs.get(1); 
    
    assertEquals(firstSimulationConfig, secondSimulationConfig); 
    
    assertTrue(firstSimulationConfig.getSimulationRuns() == 1); 
    
    List<IItemConfig> itemConfigs = new LinkedList<IItemConfig>(); 
    itemConfigs.add(new ItemConfig("testItem", 1)); 
    
    assertEquals(firstSimulationConfig.getTConfig(), itemConfigs); 
    
    List<IValuationConfig> valuationConfigs = new LinkedList<IValuationConfig>(); 
    List<String> tradeableNames = new LinkedList<String>(); 
    tradeableNames.add("testItem"); 
    
    Class<?> distributionClass =
        Class.forName("brown.auction.value.distribution.library.AdditiveValuationDistribution");
    Constructor<?> distributionCons =
        distributionClass.getConstructor(ICart.class, List.class);
    Map<Constructor<?>, List<Double>> generators = new HashMap<Constructor<?>, List<Double>>(); 
    
    Class<?> generatorClass = Class.forName(
        "brown.auction.value.generator.library.NormalValGenerator");
    Constructor<?> generatorCons =
        generatorClass.getConstructor(List.class);
    
    List<Double> genParams = new LinkedList<Double>(); 
    genParams.add(0.0); 
    genParams.add(1.0); 
    
    generators.put(generatorCons, genParams); 
    
    valuationConfigs.add(new ValuationConfig(tradeableNames, distributionCons, generators)); 
    assertEquals(firstSimulationConfig.getVConfig(), valuationConfigs); 
    
    List<IEndowmentConfig> eConfigs = new LinkedList<IEndowmentConfig>(); 
    
    Map<String, Integer> endowmentMapping = new HashMap<String, Integer>(); 
    endowmentMapping.put("testItem", 1);
    eConfigs.add(new EndowmentConfig("default", endowmentMapping, 100.0)); 
    assertEquals(firstSimulationConfig.getEConfig(), eConfigs); 
    
    List<List<IMarketConfig>> marketConfigs = new LinkedList<List<IMarketConfig>>(); 
    List<IMarketConfig> simMarkets = new LinkedList<IMarketConfig>(); 
    
    simMarkets.add(new MarketConfig(new FlexibleRules(new SimpleHighestPriceAllocation(), 
        new SecondPricePayment(), new SimpleOneSidedQuery(), 
        new SimpleOneShotActivity(), new AnonymousPolicy(), 
        new NoInnerIR(), new OneShotTermination()), tradeableNames)); 
    simMarkets.add(new MarketConfig(new FlexibleRules(new SimpleHighestPriceAllocation(), 
        new SecondPricePayment(), new SimpleOneSidedQuery(), 
        new SimpleOneShotActivity(), new AnonymousPolicy(), 
        new NoInnerIR(), new OneShotTermination()), tradeableNames));
    marketConfigs.add(simMarkets); 
    assertEquals(firstSimulationConfig.getMConfig(), marketConfigs); 
    
  }
}
