package brown.user.main;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.auction.rules.IActivityRule;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IPaymentRule;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;
import brown.auction.value.generator.IValuationGenerator;
import brown.logging.library.TestLogging;
import brown.platform.market.library.AbsMarketRules;
import brown.platform.market.library.FlexibleRules;

public class CommandLineParser implements ICommandLineParser {

  @Override
  public SimulationConfig parseCommandLine(int numRuns, int delayTime,
      String tTypeString, int numTradeables, String distributionString,
      String generatorString,
      int endowmentNumTradeables, double endowmentMoney, String aRuleString,
      String pRuleString, String qRuleString, String actRuleString,
      String irPolicyString, String tConditionString) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
  InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

    
    
    TestLogging.log(numRuns); 
    TestLogging.log(delayTime); 
    TestLogging.log(tTypeString); 
    TestLogging.log(numTradeables); 
    TestLogging.log(distributionString); 
    TestLogging.log(generatorString);  
    TestLogging.log(endowmentNumTradeables);   
    TestLogging.log(endowmentMoney); 
    TestLogging.log(aRuleString); 
    TestLogging.log(pRuleString); 
    TestLogging.log(qRuleString); 
    TestLogging.log(actRuleString); 
    TestLogging.log(irPolicyString); 
    TestLogging.log(tConditionString); 
    
    Class<?> tTypeClass = Class.forName("brown.mechanism.tradeable.library." + tTypeString);
    Class<?> generatorClass = Class.forName("brown.auction.value.generator.library." + generatorString);
    Class<?> distributionClass = Class.forName("brown.auction.value.distribution.library." + distributionString);
    Class<?> aRuleClass = Class.forName("brown.auction.rules.allocation.onesided." + aRuleString);
    Class<?> pRuleClass = Class.forName("brown.auction.rules.payment.onesided." + pRuleString);
    Class<?> qRuleClass = Class.forName("brown.auction.rules.query.onesided." + qRuleString);
    Class<?> actRuleClass = Class.forName("brown.auction.rules.activity.onesided." + actRuleString);
    Class<?> irPolicyClass = Class.forName("brown.auction.rules.ir.onesided." + irPolicyString);
    Class<?> tConditionClass = Class.forName("brown.auction.rules.termination.onesided." + tConditionString);

    Constructor<?> tTypeCons = tTypeClass.getConstructor(Integer.class);
    Constructor<?> generatorCons = generatorClass.getConstructor(Double.class, Double.class);
    Constructor<?> distributionCons = distributionClass.getConstructor(IValuationGenerator.class, Set.class);
    Constructor<?> aRuleCons = aRuleClass.getConstructor();
    Constructor<?> pRuleCons = pRuleClass.getConstructor();
    Constructor<?> qRuleCons = qRuleClass.getConstructor();
    Constructor<?> actRuleCons = actRuleClass.getConstructor();
    Constructor<?> irPolicyCons = irPolicyClass.getConstructor();
    Constructor<?> tConditionCons = tConditionClass.getConstructor();

    // constructors


    AbsMarketRules marketRule = new FlexibleRules((IAllocationRule) aRuleCons.newInstance(),
            (IPaymentRule) pRuleCons.newInstance(),
            (IQueryRule) qRuleCons.newInstance(),
            (IActivityRule) actRuleCons.newInstance(),
            (IInformationRevelationPolicy) irPolicyCons.newInstance(),
            (ITerminationCondition) tConditionCons.newInstance());
    List<Double> generatorArgs = new LinkedList<Double>(); 
    generatorArgs.add(1.0); 
    generatorArgs.add(0.0); 
    
    List<AbsMarketRules> rules = new LinkedList<>();
    rules.add(marketRule);

    
    // tradeableConfig
    Map<Constructor<?>, List<Double>> generators = new HashMap<Constructor<?>, List<Double>>(); 
    generators.put(generatorCons, generatorArgs); 
    List <ITradeableConfig> tConfigList = new LinkedList<ITradeableConfig>(); 
    ITradeableConfig tConfig = new TradeableConfig("default", tTypeCons, numTradeables, distributionCons, generators); 
    tConfigList.add(tConfig); 
    
    // endowmentConfig
    List <IEndowmentConfig> eConfigList = new LinkedList<IEndowmentConfig>(); 
    Map<String, Integer> tMap = new HashMap<String, Integer>(); 
    tMap.put("default", endowmentNumTradeables); 
    IEndowmentConfig eConfig = new EndowmentConfig("default", tMap, endowmentMoney); 
    eConfigList.add(eConfig);
    
    // marketConfig
    Map<String, Integer> mMap = new HashMap<String, Integer>(); 
    mMap.put("default", numTradeables); 
    List<List<IMarketConfig>> mConfigSquared = new LinkedList<List<IMarketConfig>>(); 
    List<IMarketConfig> mConfigList = new LinkedList<IMarketConfig>(); 
    IMarketConfig mConfig = new MarketConfig(marketRule, tMap);
    mConfigList.add(mConfig); 
    mConfigSquared.add(mConfigList); 
    
    // simulationConfig
    SimulationConfig config = new SimulationConfig(numRuns, tConfigList, eConfigList, mConfigSquared);
    return config; 
    
  }
  
}