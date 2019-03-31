package brown.user.main.library;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.rules.activity.onesided.OneShotActivity;
import brown.auction.rules.allocation.onesided.HighestPriceAllocation;
import brown.auction.rules.ir.onesided.AnonymousPolicy;
import brown.auction.rules.payment.onesided.SecondPricePayment;
import brown.auction.rules.query.onesided.SimpleQuery;
import brown.auction.rules.termination.onesided.OneShotTermination;
import brown.platform.market.IFlexibleRules;
import brown.platform.market.library.FlexibleRules;
import brown.platform.tradeable.ITradeable;
import brown.platform.tradeable.library.Tradeable;
import brown.user.main.ICommandLineParser;
import brown.user.main.IEndowmentConfig;
import brown.user.main.IMarketConfig;
import brown.user.main.ISimulationConfig;
import brown.user.main.ITradeableConfig;
import brown.user.main.IValuationConfig;


public class CommandLineParserTest {
  
  @Test
  public void testCommandLineParser() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    
    int numRuns = 1; 
    int delayTime = 1; 
    String tTypeString = "SimpleTradeable"; 
    int numTradeables = 5; 
    String distributionString = "AdditiveValuationDistribution"; 
    String generatorString = "NormalValGenerator"; 
    int endowmentNumTradeables = 4; 
    double endowmentMoney = 100.0; 
    String aRuleString = "HighestPriceAllocation"; 
    String pRuleString = "SecondPricePayment"; 
    String qRuleString = "SimpleQuery"; 
    String actRuleString = "OneShotActivity"; 
    String irPolicyString = "AnonymousPolicy"; 
    String tConditionString = "OneShotTermination"; 
    
    
    ICommandLineParser p = new CommandLineParser(); 
    ISimulationConfig sConfig = p.parseCommandLine(numRuns, delayTime,
        endowmentNumTradeables, tTypeString, numTradeables, distributionString, generatorString,
        endowmentNumTradeables, endowmentMoney, aRuleString, pRuleString,
        qRuleString, actRuleString, irPolicyString, tConditionString);  
    
    Map<String, Integer> eMap = new HashMap<String, Integer>(); 
    eMap.put("default", endowmentNumTradeables); 
    IEndowmentConfig eConfig = new EndowmentConfig("default", eMap, endowmentMoney);
    List<IEndowmentConfig> eConfigList = new LinkedList<IEndowmentConfig>(); 
    eConfigList.add(eConfig); 
    
    // tradeableConfigs
    List<ITradeable> allTradeables = new LinkedList<ITradeable>();
    for (int i = 0; i < numTradeables; i++) {
      allTradeables.add(new Tradeable(i, "default")); 
    }
    
    // tradeable config
    Class<?> tTypeClass = Class.forName("brown.platform.tradeable.library." + tTypeString);
    Constructor<?> tTypeCons = tTypeClass.getConstructor(Integer.class);
    List <ITradeableConfig> tConfigList = new LinkedList<ITradeableConfig>(); 
    ITradeableConfig tConfig = new TradeableConfig("default", tTypeCons, allTradeables.size()); 
    tConfigList.add(tConfig); 
    
    // valconfig
    Class<?> generatorClass = Class.forName("brown.auction.value.generator.library." + generatorString);
    Class<?> distributionClass = Class.forName("brown.auction.value.distribution.library." + distributionString);
    Constructor<?> generatorCons = generatorClass.getConstructor(List.class);
    Constructor<?> distributionCons = distributionClass.getConstructor(Map.class, List.class);
    Map<Constructor<?>, List<Double>> generators = new HashMap<Constructor<?>, List<Double>>(); 
    List<Double> params = new LinkedList<Double>(); 
    params.add(0.0); 
    params.add(1.0); 
    generators.put(generatorCons, params); 
    
    List<IValuationConfig> vConfigList = new LinkedList<IValuationConfig>();
    List<String> goods = new LinkedList<String>(); 
    goods.add("default"); 
    vConfigList.add(new ValuationConfig(goods, distributionCons, generators));
    
    
    // marketConfig
    IFlexibleRules marketRule = new FlexibleRules(new HighestPriceAllocation(),
        new SecondPricePayment(),
        new SimpleQuery(),
        new OneShotActivity(),
        new AnonymousPolicy(),
        new OneShotTermination());
    // marketConfig
    List<String> mList = new LinkedList<String>(); 
    mList.add("default"); 
    List<List<IMarketConfig>> mConfigSquared = new LinkedList<List<IMarketConfig>>(); 
    List<IMarketConfig> mConfigList = new LinkedList<IMarketConfig>(); 
    IMarketConfig mConfig = new MarketConfig(marketRule, mList);
    mConfigList.add(mConfig); 
    mConfigSquared.add(mConfigList); 
    
    ISimulationConfig testConfig = new SimulationConfig(numRuns, tConfigList, vConfigList, eConfigList, mConfigSquared); 
    
    //assertEquals(sConfig, testConfig); 
  }
  
}
