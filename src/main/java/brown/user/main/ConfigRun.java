package brown.user.main;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
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
import brown.platform.input.config.IEndowmentConfig;
import brown.platform.input.config.IMarketConfig;
import brown.platform.input.config.ITradeableConfig;
import brown.platform.input.config.library.SimulationConfig;
import brown.platform.market.IMarketManager;
import brown.platform.market.IMarketRules;
import brown.platform.market.library.MarketManager;
import brown.platform.simulator.ISimulationManager;
import brown.platform.simulator.library.SimulationManager;
import brown.platform.whiteboard.IWhiteboard;
import brown.platform.whiteboard.library.Whiteboard;
import brown.platform.world.IDomainManager;
import brown.platform.world.IWorldManager;
import brown.platform.world.library.DomainManager;
import brown.platform.world.library.WorldManager;


public class ConfigRun {

    private Integer delayTime;
    private List<SimulationConfig> config;

    public ConfigRun(Integer delayTime, List<SimulationConfig> config) {
        this.delayTime = delayTime;
        this.config = config;
    }

    public void run(Integer numSimulations) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ISimulationManager simulationManager = new SimulationManager();
        for (SimulationConfig aConfig : this.config) {
            IWorldManager worldManager = new WorldManager();
            IDomainManager domainManager = new DomainManager();
            IEndowmentManager endowmentManager = new EndowmentManager();
            IAccountManager accountManager = new AccountManager();
            IValuationManager valuationManager = new ValuationManager();
            ITradeableManager tradeableManager = new TradeableManager();
            IMarketManager marketManager = new MarketManager();
            IWhiteboard whiteboard = new Whiteboard();
           
            
            
            // tradeable manager should be easy so gonna start here. 
            List<ITradeableConfig> tradeableConfig = aConfig.getTConfig(); 
            for (ITradeableConfig tConfig : tradeableConfig) {
              tradeableManager.createTradeables(tConfig.getTradeableName(), tConfig.getTType(), tConfig.getNumTradeables()); 
            }
            
            // valuation manager
            for (ITradeableConfig tConfig : tradeableConfig) {
              valuationManager.createValuation(tConfig.getTradeableName(), tConfig.getValDistribution(), tConfig.getGenerator(),
                  new HashSet<ITradeable>(tradeableManager.getTradeables(tConfig.getTradeableName())));
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
            
            // todo: initialization for the whiteboard? 
            
            
            // todo: history config? 
            
            
            // todo: refactor account manager b/c endowments? 
            
            
            domainManager.createDomain(tradeableManager, valuationManager, accountManager, endowmentManager);
            worldManager.createWorld(domainManager, marketManager, whiteboard);
            simulationManager.createSimulation(worldManager);
            tradeableManager.lock();
            valuationManager.lock();
            simulationManager.lock();
            marketManager.lock();
        }


        simulationManager.runSimulation(delayTime, numSimulations);
    }
}
