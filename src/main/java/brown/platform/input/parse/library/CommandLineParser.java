package brown.platform.input.parse.library;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.auction.rules.IActivityRule;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IPaymentRule;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;
import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.logging.library.TestLogging;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.input.config.library.SimulationConfig;
import brown.platform.input.parse.ICommandLineParser;
import brown.platform.market.library.AbsMarketRules;
import brown.platform.market.library.FlexibleRules;
import brown.platform.market.library.SimultaneousMarket;

public class CommandLineParser implements ICommandLineParser {

  @Override
  public SimulationConfig parseCommandLine(int numRuns, int delayTime,
      String tTypeString, int numTradeables, String distributionString,
      String generatorString, String endowmenttTypeString,
      int endowmentNumTradeables, int endowmentMoney, String aRuleString,
      String pRuleString, String qRuleString, String actRuleString,
      String irPolicyString, String tConditionString) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
  InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

    
    
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
    TestLogging.log(pRuleString); 
    TestLogging.log(qRuleString); 
    TestLogging.log(actRuleString); 
    TestLogging.log(irPolicyString); 
    TestLogging.log(tConditionString); 
    
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

    SimulationConfig config = new SimulationConfig(1, allTradeables, endowTradeables, endowmentMoney, blocks, valGenerator, valDistribution);
    
    return config; 
  }
  
  
}