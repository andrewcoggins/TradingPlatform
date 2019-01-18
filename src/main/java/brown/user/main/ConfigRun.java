package brown.user.main;

import java.util.List;

import brown.auction.value.manager.IValuationManager;
import brown.auction.value.manager.ValuationManager;
import brown.mechanism.tradeable.ITradeableManager;
import brown.mechanism.tradeable.library.TradeableManager;
import brown.platform.accounting.IAccountManager;
import brown.platform.accounting.IEndowmentManager;
import brown.platform.accounting.library.AccountManager;
import brown.platform.accounting.library.EndowmentManager;
import brown.platform.input.config.IMarketConfig;
import brown.platform.input.config.library.SimulationConfig;
import brown.platform.market.IMarketManager;
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

    public void run(Integer numSimulations) {
        ISimulationManager simulationManager = new SimulationManager();
        for (SimulationConfig aConfig : this.config) {
            IWorldManager worldManager = new WorldManager();
            IMarketManager marketManager = new MarketManager();
            IDomainManager domainManager = new DomainManager();
            IEndowmentManager endowmentManager = new EndowmentManager();
            IAccountManager accountManager = new AccountManager();
            IValuationManager valuationManager = new ValuationManager();
            ITradeableManager tradeableManager = new TradeableManager();
            IWhiteboard whiteboard = new Whiteboard();
            
            // for the market manager, gonna need the rules, map, and the mustinclude
            for (List<IMarketConfig> mConfig : aConfig.getMConfig()) {
                marketManager.createSimultaneousMarket(mConfig);
            }
            
            
            endowmentManager.createEndowment(aConfig.getEndowMoney, aConfig.getEndowTradeables);
            
            valuationManager.createValuation(0, aConfig.distribution);
            tradeableManager.createTradeables(aConfig.allTradeables.size());
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
