package brown.user.main;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.value.manager.IValuationManager;
import brown.auction.value.manager.library.ValuationManager;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.ITradeableManager;
import brown.mechanism.tradeable.library.TradeableManager;
import brown.platform.accounting.IAccountManager;
import brown.platform.accounting.IEndowmentManager;
import brown.platform.accounting.library.AccountManager;
import brown.platform.accounting.library.EndowmentManager;
import brown.platform.market.IMarketManager;
import brown.platform.market.IMarketRules;
import brown.platform.market.library.MarketManager;
import brown.platform.simulator.ISimulationManager;
import brown.platform.simulator.library.SimulationManager;
import brown.platform.world.IDomainManager;
import brown.platform.world.IWorldManager;
import brown.platform.world.library.DomainManager;
import brown.platform.world.library.WorldManager;


public class ConfigRun {

    private List<SimulationConfig> config;

    public ConfigRun(List<SimulationConfig> config) {
        this.config = config;
    }

    public void run(Integer delayTime, Integer numSimulations) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {
        ISimulationManager simulationManager = new SimulationManager();
        for (SimulationConfig aConfig : this.config) {
            IWorldManager worldManager = new WorldManager();
            IDomainManager domainManager = new DomainManager();
            IEndowmentManager endowmentManager = new EndowmentManager();
            IAccountManager accountManager = new AccountManager();
            IValuationManager valuationManager = new ValuationManager();
            ITradeableManager tradeableManager = new TradeableManager();
            IMarketManager marketManager = new MarketManager();
           
            
            // tradeable manager should be easy so gonna start here. 
            
            List<ITradeableConfig> tradeableConfig = aConfig.getTConfig(); 
            for (ITradeableConfig tConfig : tradeableConfig) {
              tradeableManager.createTradeables(tConfig.getTradeableName(), tConfig.getTType(), tConfig.getNumTradeables()); 
            }
            
            // valuation manager. 
            
            List<IValuationConfig> vConfigs = aConfig.getVConfig(); 
            for (IValuationConfig vConfig : vConfigs) {
              List<String> tradeableNames = vConfig.getTradeableNames();
              Map<String, List<ITradeable>> valuationTradeables = new HashMap<String, List<ITradeable>>(); 
              for (String s : tradeableNames) {
                valuationTradeables.put(s, tradeableManager.getTradeables(s)); 
              }
              valuationManager.createValuation(vConfig.getValDistribution(), vConfig.getGenerators(), valuationTradeables);
            }
            
            // endowments also need a tradeable map, and a tradeable config. 
            Map<String, List<ITradeable>> allTradeables = new HashMap<String, List<ITradeable>>();  
            for (ITradeableConfig tConfig : tradeableConfig) {
              allTradeables.put(tConfig.getTradeableName(), tradeableManager.getTradeables(tConfig.getTradeableName())); 
            }
            
            for (IEndowmentConfig eConfig: aConfig.getEConfig()) {
              
              endowmentManager.createEndowment(eConfig.getName(), eConfig.getEndowmentMapping(),
                  eConfig.getIncludeMapping(), eConfig.getFrequency(), allTradeables, eConfig.getMoney());
            }
            
            
            // for the market manager, gonna need the rules, map, and the mustInclude
            for (List<IMarketConfig> mConfigList : aConfig.getMConfig()) {
                List<IMarketRules> marketRules = new LinkedList<IMarketRules>(); 
                List<Map<String, Integer>> marketTradeables = new LinkedList<Map<String, Integer>>(); 
                for (IMarketConfig mConfig : mConfigList) {
                  marketRules.add(mConfig.getRules()); 
                  marketTradeables.add(mConfig.getNumTradeablesMap()); 
                }
                marketManager.createSimultaneousMarket(marketRules, marketTradeables, allTradeables);
            }
            
            // todo: refactor account manager b/c endowments? 
            
            
            domainManager.createDomain(tradeableManager, valuationManager, accountManager, endowmentManager);
            worldManager.createWorld(domainManager, marketManager);
            simulationManager.createSimulation(worldManager);
            tradeableManager.lock();
            valuationManager.lock();
            simulationManager.lock();
            marketManager.lock();
        }


        simulationManager.runSimulation(delayTime, numSimulations);
    }
}
