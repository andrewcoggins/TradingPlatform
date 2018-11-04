package brown.user.server; 

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.auction.marketstate.library.MarketState;
import brown.auction.preset.FlexibleRules;
import brown.auction.prevstate.library.BlankStateInfo;
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
import brown.platform.market.IMarket;
import brown.platform.market.IMarketManager;
import brown.platform.market.library.History;
import brown.platform.market.library.Market;
import brown.platform.market.library.MarketManager;
import brown.platform.market.library.SimultaneousMarket;
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
public class SSFPServer {

  /**
   *
   * @param args
   * 0: numruns : int
   * 1: tradeabletype : string
   * 2: numtradeables : int
   * 3: distribution
   * 4: generator
   * 5: endowment tradeable type
   * 6: endowment num tradeables
   * 7: endowment money
   * 8: allocationrule
   * 9: paymentrule
   * 10: queryrule
   * 11: activityrule
   * 12: ir policy
   * 13: termination condition
   * @throws InterruptedException
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

    Class<?> tTypeClass = Class.forName(tTypeString);
    Class<?> generatorClass = Class.forName(generatorString);
    Class<?> distributionClass = Class.forName(distributionString);
    Class<?> endowmenttTypeClass = Class.forName(endowmenttTypeString);
    Class<?> aRuleClass = Class.forName(aRuleString);
    Class<?> pRuleClass = Class.forName(pRuleString);
    Class<?> qRuleClass = Class.forName(qRuleString);
    Class<?> actRuleClass = Class.forName(actRuleString);
    Class<?> irPolicyClass = Class.forName(irPolicyString);
    Class<?> tConditionClass = Class.forName(tConditionString);

    Constructor<?> tTypeCons = tTypeClass.getConstructor(Integer.TYPE);
    Constructor<?> generatorCons = generatorClass.getConstructor(Double.TYPE, Double.TYPE);
    Constructor<?> distributionCons = distributionClass.getConstructor(IValuationGenerator.class, Set.class);
    Constructor<?> endowmentTypeCons = endowmenttTypeClass.getConstructor(Integer.TYPE);
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
    IValuationDistribution valDistribution = (IValuationDistribution) distributionCons.newInstance(valGenerator, allTradeables);
    IWorldManager worldManager = new WorldManager();
    IMarketManager marketManager = new MarketManager();
    IDomainManager domainManager = new DomainManager();
    IEndowmentManager endowmentManager = new EndowmentManager();
    IAccountManager accountManager = new AccountManager();
    IValuationManager valuationManager = new ValuationManager();
    ITradeableManager tradeableManager = new TradeableManager();
    ISimulationManager simulationManager = new SimulationManager();
    IWhiteboard whiteboard = new Whiteboard();

    //TODO: clean market manager. This needs a little work.
    IMarket market = new Market(new FlexibleRules((IAllocationRule) aRuleCons.newInstance(),
            (IPaymentRule) pRuleCons.newInstance(),
            (IQueryRule) qRuleCons.newInstance(),
            new OneGrouping(),
            (IActivityRule) actRuleCons.newInstance(),
            (IInformationRevelationPolicy) irPolicyCons.newInstance(),
            (ITerminationCondition) tConditionCons.newInstance(),
            new NoRecordKeeping()),
            new MarketState(0, allTradeables, new BlankStateInfo()), new History());
    List<IMarket> marketList = new LinkedList<>();
    marketList.add(market);
    SimultaneousMarket sMarket = new SimultaneousMarket(marketList);
    ((MarketManager) marketManager).addSimulMarket(sMarket, allTradeables, null);
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