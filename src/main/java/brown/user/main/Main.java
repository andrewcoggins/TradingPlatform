package brown.user.main;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import brown.auction.rules.*;
import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.logging.library.ErrorLogging;
import brown.mechanism.tradeable.ITradeable;

import java.lang.reflect.Constructor;

import brown.platform.market.library.AbsMarketRules;
import brown.platform.market.library.FlexibleRules;
import brown.platform.market.library.SimultaneousMarket;
import brown.logging.library.TestLogging;

/**
 * runs a simple simultaneous first price auction.
 * @author acoggins
 *
 */
public class Main {

  /**
   *
   * @param args
   * 1
   * 0: numruns : int
   * 5
   * 1: delayTime: int
   * SimpleTradeable
   * 2: tradeabletype : string
   * 1
   * 3: numtradeables : int
   * AdditiveValuationDistribution
   * 4: distribution
   * NormalValGenerator
   * 5: generator
   * SimpleTradeable
   * 6: endowment tradeable type
   * 0
   * 7: endowment num tradeables
   * 1000
   * 8: endowment money
   * HighestPriceAllocation
   * 9: allocationrule
   * SimpleSecondPricePayment
   * 10: paymentrule
   * SimpleQuery
   * 11: queryrule
   * OneShotActivity
   * 12: activityrule
   * SSSPAnonymousPolicy
   * 13: ir policy
   * OneShotTermination
   * 14: termination condition
   */

  public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
          InstantiationException, IllegalAccessException, InvocationTargetException {
     
    Integer numRuns;
    Integer delayTime;
    String tTypeString;
    Integer numTradeables;
    String distributionString;
    String generatorString;
    String endowmenttTypeString;
    Integer endowmentNumTradeables;
    Integer endowmentMoney;
    String aRuleString; 
    String pRuleString; 
    String qRuleString;
    String actRuleString; 
    String irPolicyString; 
    String tConditionString; 

    
    String inputType = args[0]; 
    TestLogging.log(inputType); 
    if (inputType.equals("args")) {
      numRuns = new Integer(args[1]);
      delayTime = new Integer(args[2]);
      tTypeString = args[3];
      numTradeables = new Integer(args[4]);
      distributionString = args[5];
      generatorString = args[6];
      endowmenttTypeString = args[7];
      endowmentNumTradeables = new Integer(args[8]);
      endowmentMoney = new Integer(args[9]);
      aRuleString = args[10];
      pRuleString = args[11];
      qRuleString = args[12];
      actRuleString = args[13];
      irPolicyString = args[14];
      tConditionString = args[15];
      
      
      TestLogging.log(numRuns); 
      TestLogging.log(delayTime); 
      TestLogging.log(tTypeString); 
      TestLogging.log(numTradeables); 
      TestLogging.log(distributionString); 
      TestLogging.log(generatorString); 
      TestLogging.log(endowmenttTypeString); 
      TestLogging.log(endowmentNumTradeables); 
      TestLogging.log(endowmentMoney); 
      TestLogging.log(aRuleString); 
      TestLogging.log(aRuleString); 
      TestLogging.log(qRuleString); 
      TestLogging.log(actRuleString); 
      TestLogging.log(irPolicyString); 
      TestLogging.log(tConditionString); 
      

    } else if (inputType == "json") {
      numRuns = new Integer(args[0]);
      delayTime = new Integer(args[1]);
      tTypeString = args[2];
      numTradeables = new Integer(args[3]);
      distributionString = args[4];
      generatorString = args[5];
      endowmenttTypeString = args[6];
      endowmentNumTradeables = new Integer(args[7]);
      endowmentMoney = new Integer(args[8]);
      aRuleString = args[9];
      pRuleString = args[10];
      qRuleString = args[11];
      actRuleString = args[12];
      irPolicyString = args[13];
      tConditionString = args[14];
    } else {
      numRuns = 0;
      delayTime = 0;
      tTypeString = "";
      numTradeables = 0;
      distributionString = "";
      generatorString = "";
      endowmenttTypeString = "";
      endowmentNumTradeables = 0;
      endowmentMoney = 0;
      aRuleString = "";
      pRuleString = "";
      qRuleString = "";
      actRuleString = "";
      irPolicyString = "";
      tConditionString = "";
      ErrorLogging.log("ERROR: no input specification given.");
      System.exit(1);
    } 
    
    Class<?> tTypeClass = Class.forName("brown.mechanism.tradeable.library." + tTypeString);
    Class<?> generatorClass = Class.forName("brown.auction.value.generator.library." + generatorString);
    Class<?> distributionClass = Class.forName("brown.auction.value.distribution.library." + distributionString);
    Class<?> endowmenttTypeClass = Class.forName("brown.mechanism.tradeable.library." + endowmenttTypeString);
    Class<?> aRuleClass = Class.forName("brown.auction.rules.library." + aRuleString);
    Class<?> pRuleClass = Class.forName("brown.auction.rules.library." + pRuleString);
    Class<?> qRuleClass = Class.forName("brown.auction.rules.library." + qRuleString);
    Class<?> actRuleClass = Class.forName("brown.auction.rules.library." + actRuleString);
    Class<?> irPolicyClass = Class.forName("brown.auction.rules.library." + irPolicyString);
    Class<?> tConditionClass = Class.forName("brown.auction.rules.library." + tConditionString);

    Constructor<?> tTypeCons = tTypeClass.getConstructor(Integer.class);
    Constructor<?> generatorCons = generatorClass.getConstructor(Double.class, Double.class);
    Constructor<?> distributionCons = distributionClass.getConstructor(IValuationGenerator.class, Set.class);
    Constructor<?> endowmentTypeCons = endowmenttTypeClass.getConstructor(Integer.class);
    Constructor<?> aRuleCons = aRuleClass.getConstructor();
    Constructor<?> pRuleCons = pRuleClass.getConstructor();
    Constructor<?> qRuleCons = qRuleClass.getConstructor();
    Constructor<?> actRuleCons = actRuleClass.getConstructor();
    Constructor<?> irPolicyCons = irPolicyClass.getConstructor();
    Constructor<?> tConditionCons = tConditionClass.getConstructor();

    // constructors

    List<ITradeable> allTradeables = new LinkedList<>();
    for (int i = 0; i < numTradeables; i++){
      allTradeables.add((ITradeable) tTypeCons.newInstance(i));
    }
    List<ITradeable> endowTradeables = new LinkedList<>();
    for (int i = 0; i < endowmentNumTradeables; i++){
      endowTradeables.add((ITradeable) endowmentTypeCons.newInstance(i));
    }

    AbsMarketRules marketRule = new FlexibleRules((IAllocationRule) aRuleCons.newInstance(),
            (IPaymentRule) pRuleCons.newInstance(),
            (IQueryRule) qRuleCons.newInstance(),
            (IActivityRule) actRuleCons.newInstance(),
            (IInformationRevelationPolicy) irPolicyCons.newInstance(),
            (ITerminationCondition) tConditionCons.newInstance());
    IValuationGenerator valGenerator = (IValuationGenerator) generatorCons.newInstance(1.0, 0.0);
    IValuationDistribution valDistribution = (IValuationDistribution) distributionCons.newInstance(valGenerator,
            new HashSet<>(allTradeables));
    List<AbsMarketRules> rules = new LinkedList<>();
    rules.add(marketRule);
    List<SimultaneousMarket> blocks = new LinkedList<>();
    SimultaneousMarket block = new SimultaneousMarket(rules);
    blocks.add(block);

    List<SimulationConfig> configs = new LinkedList<>();

    SimulationConfig config = new SimulationConfig(1, allTradeables, endowTradeables, endowmentMoney, blocks, valGenerator, valDistribution);
    configs.add(config);
    ConfigRun configRun = new ConfigRun(delayTime, configs);
    configRun.run(numRuns);
  }
}