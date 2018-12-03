package brown.user.main;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import brown.auction.rules.*;
import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.mechanism.tradeable.ITradeable;

import java.lang.reflect.Constructor;

import brown.platform.market.library.AbsMarketRules;
import brown.platform.market.library.FlexibleRules;
import brown.platform.market.library.SimultaneousMarket;

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
    Integer numRuns = new Integer(args[0]);
    Integer delayTime = new Integer(args[1]);
    String tTypeString = args[2];
    Integer numTradeables = new Integer(args[3]);
    String distributionString = args[4];
    String generatorString = args[5];
    String endowmenttTypeString = args[6];
    Integer endowmentNumTradeables = new Integer(args[7]);
    Integer endowmentMoney = new Integer(args[8]);
    String aRuleString = args[9];
    String pRuleString = args[10];
    String qRuleString = args[11];
    String actRuleString = args[12];
    String irPolicyString = args[13];
    String tConditionString = args[14];

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