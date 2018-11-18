package brown.user.main;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import brown.auction.preset.AbsMarketRules;
import brown.auction.preset.FlexibleRules;
import brown.auction.rules.*;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneGrouping;
import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.manager.ValuationManager;
import brown.auction.value.manager.IValuationManager;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.ITradeableManager;
import brown.mechanism.tradeable.library.TradeableManager;
import brown.platform.accounting.library.AccountManager;
import brown.platform.accounting.IAccountManager;
import java.lang.reflect.Constructor;

import brown.platform.accounting.library.EndowmentManager;
import brown.platform.accounting.IEndowmentManager;
import brown.platform.market.IMarketManager;
import brown.platform.market.library.MarketManager;
import brown.platform.simulation.ISimulationManager;
import brown.platform.simulation.library.SimulationManager;
import brown.platform.whiteboard.IWhiteboard;
import brown.platform.world.IDomainManager;
import brown.platform.world.IWorldManager;
import brown.platform.world.library.DomainManager;
import brown.platform.world.library.WorldManager;
import brown.platform.whiteboard.library.Whiteboard;

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

    System.out.println(numRuns);
    System.out.println(delayTime);
    System.out.println(tTypeString);
    System.out.println(numTradeables);
    System.out.println(distributionString);
    System.out.println(generatorString);
    System.out.println(endowmenttTypeString);
    System.out.println(endowmentNumTradeables);
    System.out.println(endowmentMoney);
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
    IValuationGenerator valGenerator = (IValuationGenerator) generatorCons.newInstance(1.0, 0.0);
    IValuationDistribution valDistribution = (IValuationDistribution) distributionCons.newInstance(valGenerator,
            new HashSet<>(allTradeables));
    IWorldManager worldManager = new WorldManager();
    IMarketManager marketManager = new MarketManager();
    IDomainManager domainManager = new DomainManager();
    IEndowmentManager endowmentManager = new EndowmentManager();
    IAccountManager accountManager = new AccountManager();
    IValuationManager valuationManager = new ValuationManager();
    ITradeableManager tradeableManager = new TradeableManager();
    ISimulationManager simulationManager = new SimulationManager();
    IWhiteboard whiteboard = new Whiteboard();

    AbsMarketRules marketRule = new FlexibleRules((IAllocationRule) aRuleCons.newInstance(),
            (IPaymentRule) pRuleCons.newInstance(),
            (IQueryRule) qRuleCons.newInstance(),
            new OneGrouping(),
            (IActivityRule) actRuleCons.newInstance(),
            (IInformationRevelationPolicy) irPolicyCons.newInstance(),
            (ITerminationCondition) tConditionCons.newInstance(),
            new NoRecordKeeping());
    List<AbsMarketRules> marketList = new LinkedList<>();
    marketList.add(marketRule);
    marketManager.createSimultaneousMarket(marketList);
    endowmentManager.createEndowment(endowmentMoney, endowTradeables);
    valuationManager.createValuation(0, valDistribution);
    tradeableManager.createSimpleTradeables(numTradeables);
    domainManager.createDomain(tradeableManager, valuationManager, accountManager, endowmentManager);
    worldManager.createWorld(domainManager, marketManager, whiteboard);
    simulationManager.createSimulation(worldManager);

    tradeableManager.lock();
    valuationManager.lock();
    simulationManager.lock();
    marketManager.lock();

    simulationManager.runSimulation(delayTime, numRuns);
  }
}