package brown.user.main;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.auction.rules.IActivityRule;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IPaymentRule;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;
import brown.auction.value.distribution.library.AdditiveValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.generator.library.NormalValGenerator;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.mechanism.tradeable.library.TradeableType;
import brown.platform.market.IMarketRules;
import brown.platform.market.library.FlexibleRules;
import brown.user.main.IEndowmentConfig;
import brown.user.main.IMarketConfig;
import brown.user.main.ISimulationConfig;
import brown.user.main.ITradeableConfig;
import brown.user.main.library.EndowmentConfig;
import brown.user.main.library.MarketConfig;
import brown.user.main.library.SimulationConfig;
import brown.user.main.library.TradeableConfig;
import brown.user.main.library.ValuationConfig;

public class SimulationConfigTest {
  
  @Test
  public void testSimulationConfigOne() throws NoSuchMethodException, SecurityException {
    
    List<ITradeableConfig> tConfigs = new LinkedList<ITradeableConfig>(); 
    List<IValuationConfig> vConfigs = new LinkedList<IValuationConfig>(); 
    List<IEndowmentConfig> eConfigs = new LinkedList<IEndowmentConfig>(); 
    List<List<IMarketConfig>> mConfigSquared = new LinkedList<List<IMarketConfig>>(); 
    
    List<IMarketConfig> mConfigs = new LinkedList<IMarketConfig>(); 
    
    
    IAllocationRule mockAllocationRule = mock(IAllocationRule.class); 
    IPaymentRule mockPaymentRule = mock(IPaymentRule.class); 
    IQueryRule mockQueryRule = mock(IQueryRule.class);
    IActivityRule mockActivityRule = mock(IActivityRule.class); 
    IInformationRevelationPolicy mockIR = mock(IInformationRevelationPolicy.class); 
    ITerminationCondition mocktCondition = mock(ITerminationCondition.class); 
    
    IMarketRules mRules = new FlexibleRules(mockAllocationRule, mockPaymentRule, mockQueryRule, mockActivityRule, mockIR, mocktCondition); 
    Map<String, Integer> numTradeablesMap = new HashMap<String, Integer>(); 
    numTradeablesMap.put("default", 1); 
    IMarketConfig mConfig = new MarketConfig(mRules, numTradeablesMap); 
    mConfigs.add(mConfig); 
    mConfigSquared.add(mConfigs); 
    
    Constructor<?> distCons = AdditiveValuationDistribution.class.getConstructor(IValuationGenerator.class, Set.class);
    Constructor<?> tTypeCons = SimpleTradeable.class.getConstructor(Integer.class); 
    Constructor<?> gCons = NormalValGenerator.class.getConstructor(Double.class, Double.class); 
    List<Double> params = new LinkedList<Double>(); 
    params.add(0.0); 
    params.add(1.0);  
    Map<Constructor<?>, List<Double>> gMap = new HashMap<Constructor<?>, List<Double>>(); 
    gMap.put(gCons, params); 
    ITradeableConfig tConfig = new TradeableConfig("trade", tTypeCons, 10);
    tConfigs.add(tConfig);
    
    List<String> tNameList = new LinkedList<String>(); 
    tNameList.add("trade"); 
    IValuationConfig vConfig = new ValuationConfig(tNameList, distCons, gMap); 
    vConfigs.add(vConfig); 
    
    Map<String, Integer> eMap = new HashMap<String, Integer>(); 
    eMap.put("a", 1); 
    EndowmentConfig eConfig = new EndowmentConfig("trade", eMap, 100.0); 
    eConfigs.add(eConfig); 
    
    ISimulationConfig sConfig = new SimulationConfig(1, tConfigs, vConfigs, eConfigs, mConfigSquared); 
    
    assertEquals(sConfig.getSimulationRuns(), new Integer(1)); 
    assertEquals(sConfig.getEConfig(), eConfigs); 
    assertEquals(sConfig.getTConfig(), tConfigs); 
    assertEquals(sConfig.getVConfig(), vConfigs); 
    assertEquals(sConfig.getMConfig(), mConfigSquared); 
  }
  
  
  public static void main(String[] args) throws NoSuchMethodException, SecurityException {
    SimulationConfigTest t = new SimulationConfigTest(); 
    t.testSimulationConfigOne(); 
  }
}