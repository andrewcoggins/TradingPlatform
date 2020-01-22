package brown.user.main.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.rules.IActivityRule;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IInnerIRPolicy;
import brown.auction.rules.IPaymentRule;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;
import brown.auction.value.distribution.library.AdditiveValuationDistribution;
import brown.auction.value.generator.library.NormalValGenerator;
import brown.platform.item.ICart;
import brown.platform.market.IFlexibleRules;
import brown.platform.market.library.FlexibleRules;
import brown.user.main.IEndowmentConfig;
import brown.user.main.IItemConfig;
import brown.user.main.IMarketConfig;
import brown.user.main.ISimulationConfig;
import brown.user.main.IValuationConfig;

public class SimulationConfigTest {
  
  @Test
  public void testSimulationConfigOne() throws NoSuchMethodException, SecurityException, ClassNotFoundException {
    
    List<IItemConfig> tConfigs = new LinkedList<IItemConfig>(); 
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
    IInnerIRPolicy innerIR = mock(IInnerIRPolicy.class); 
    
    IFlexibleRules mRules = new FlexibleRules(mockAllocationRule, mockPaymentRule, mockQueryRule, mockActivityRule, mockIR, innerIR, mocktCondition); 
    List<String> tradeableNames = new LinkedList<String>(); 
    tradeableNames.add("default"); 
    IMarketConfig mConfig = new MarketConfig(mRules, tradeableNames); 
    mConfigs.add(mConfig); 
    mConfigSquared.add(mConfigs); 
    
    Constructor<?> distCons = AdditiveValuationDistribution.class.getConstructor(ICart.class, List.class);
    Constructor<?> gCons = NormalValGenerator.class.getConstructor(List.class); 
    List<Double> params = new LinkedList<Double>(); 
    params.add(0.0); 
    params.add(1.0);  
    
    List<Constructor<?>> endowmentList = new LinkedList<Constructor<?>>(); 
    List<List<Double>> endowmentParamList = new LinkedList<List<Double>>(); 
     
    endowmentList.add(gCons); 
    endowmentParamList.add(params); 

    
    IItemConfig tConfig = new ItemConfig("trade", 10);
    tConfigs.add(tConfig);
    
    List<String> tNameList = new LinkedList<String>(); 
    tNameList.add("trade"); 
    IValuationConfig vConfig = new ValuationConfig(tNameList, distCons, endowmentList, endowmentParamList); 
    vConfigs.add(vConfig); 
    
    
    Class<?> endowmentDistributionClass =
        Class.forName("brown.auction.endowment.distribution.library.IndependentEndowmentDist");
    Constructor<?> endowmentDistributionCons =
        endowmentDistributionClass.getConstructor(ICart.class, List.class);

    
    List<Constructor<?>> genList = new LinkedList<Constructor<?>>(); 
    List<List<Double>> paramList = new LinkedList<List<Double>>(); 

    Class genClass = Class.forName("brown.auction.value.generator.library.NormalValGenerator"); 
    List<Double> genParams = new LinkedList<Double>(); 
    genParams.add(0.0); 
    genParams.add(100.0); 
    
    Constructor<?> genCons = genClass.getConstructor(List.class); 
    genList.add(genCons); 
    paramList.add(genParams); 
    
    
    Map<String, Integer> endowmentMapping = new HashMap<String, Integer>(); 
    endowmentMapping.put("trade", 1);
    
    
    EndowmentConfig eConfig = new EndowmentConfig(tradeableNames, endowmentDistributionCons, genList, paramList);  
    eConfigs.add(eConfig); 
    
    ISimulationConfig sConfig = new SimulationConfig(1, -1, tConfigs, vConfigs, eConfigs, mConfigSquared); 
    
    assertTrue(sConfig.getSimulationRuns() == 1); 
    assertEquals(sConfig.getEConfig(), eConfigs); 
    assertEquals(sConfig.getTConfig(), tConfigs); 
    assertEquals(sConfig.getVConfig(), vConfigs); 
    assertEquals(sConfig.getMConfig(), mConfigSquared); 
  }
  
}
