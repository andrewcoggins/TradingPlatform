package brown.user.server; 

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.auction.preset.AbsMarketRules;
import brown.auction.preset.SSFPNoRecord;
import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.manager.ValuationManager;
import brown.auction.value.manager.IValuationManager
import brown.auction.value.manager.library.AdditiveUniformConfig;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.ITradeableManager;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.mechanism.tradeable.library.TradeableManager;
import brown.platform.accounting.library.AccountManager;
import brown.platform.accounting.IAccountManager;
import java.lang.reflect.Constructor;
import java.util.Set;

import brown.platform.accounting.library.EndowmentManager;
import brown.platform.accounting.IEndowmentManager;
import brown.platform.server.library.RunServer;
import brown.platform.server.library.SimulMarkets;
import brown.platform.server.library.Simulation;
import brown.platform.world.IDomainManager;
import brown.platform.world.IWorldManager;
import brown.platform.world.library.DomainManager;
import brown.platform.world.library.WorldManager;
import brown.system.setup.library.SSSPSetup;

/**
 * runs a simple simultaneous first price auction.
 * @author acoggins
 *
 */
public class SSFPServer { 
  
  private static int numSims = 5;
  private static int numTradeables = 7;
  private static int delayTime = 5; 
  private static int lag = 300;

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

  public static void main(String[] args) throws InterruptedException, ClassNotFoundException, NoSuchMethodException,
          InstantiationException, IllegalAccessException, InvocationTargetException {
    Integer numRuns = new Integer(args[0]);
    String tTypeString = args[1];
    Integer numTradeables = new Integer(args[2]);
    String distributionString = args[3];
    String generatorString = args[4];
    String endowmenttTypeString = args[5];
    Integer endowmentNumTradeables = new Integer(args[6]);
    Integer endowmentMoney = new Integer(args[7]);
    String aRuleString = args[8];
    String pRuleString = args[9];
    String qRuleString = args[10];
    String actRuleString = args[11];
    String irPolicyString = args[12];
    String tConditionString = args[13];

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
    IDomainManager domainManager = new DomainManager();
    IEndowmentManager endowmentManager = new EndowmentManager();
    IAccountManager accountManager = new AccountManager();
    IValuationManager valuationManager = new ValuationManager();
    ITradeableManager tradeableManager = new TradeableManager();
  }

}