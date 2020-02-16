package brown.user.main.library;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;
import brown.platform.managers.IAccountManager;
import brown.platform.managers.IDomainManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.IItemManager;
import brown.platform.managers.IMarketManager;
import brown.platform.managers.ISimulationManager;
import brown.platform.managers.IValuationManager;
import brown.platform.managers.IWorldManager;
import brown.platform.managers.library.AccountManager;
import brown.platform.managers.library.DomainManager;
import brown.platform.managers.library.EndowmentManager;
import brown.platform.managers.library.ItemManager;
import brown.platform.managers.library.MarketManager;
import brown.platform.managers.library.SimulationManager;
import brown.platform.managers.library.ValuationManager;
import brown.platform.managers.library.WorldManager;
import brown.platform.market.IFlexibleRules;
import brown.user.main.IEndowmentConfig;
import brown.user.main.IItemConfig;
import brown.user.main.IMarketConfig;
import brown.user.main.ISimulationConfig;
import brown.user.main.IValuationConfig;

/**
 * take parameters from the input JSON, organize them into objects. start managers.
 * Run the simulation through the SimulationManager
 * @author andrewcoggins
 *
 */
public class ConfigRun {

  private List<ISimulationConfig> config;

  public ConfigRun(List<ISimulationConfig> config) {
    this.config = config;
  }

  public void run(Integer startingDelayTime, Double simulationDelayTime, Integer learningDelayTime, 
      Integer numSimulations, Integer serverPort, String simulationJsonFileName) throws InstantiationException,
      IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, InterruptedException {
    ISimulationManager simulationManager = new SimulationManager();
    for (ISimulationConfig aConfig : this.config) {
      IWorldManager worldManager = new WorldManager();
      IDomainManager domainManager = new DomainManager();
      IEndowmentManager endowmentManager = new EndowmentManager();
      IAccountManager accountManager = new AccountManager();
      IValuationManager valuationManager = new ValuationManager();
      IItemManager itemManager = new ItemManager();
      IMarketManager marketManager = new MarketManager();

      // item manager
      List<IItemConfig> itemConfig = aConfig.getTConfig();
      for (IItemConfig tConfig : itemConfig) {
        itemManager.createItems(tConfig.getItemName(), tConfig.getNumItems());
      }

      // valuation manager.
      List<IValuationConfig> vConfigs = aConfig.getVConfig();
      for (IValuationConfig vConfig : vConfigs) {
        List<String> itemNames = vConfig.getItemNames();
        List<IItem> valuationItems = new LinkedList<IItem>();
        vConfig.getItemNames()
            .forEach(item -> valuationItems.add(itemManager.getItems(item)));
        valuationManager.createValuation(vConfig.getValDistribution(),
            vConfig.getGeneratorConstructors(), vConfig.getGeneratorParams(), new Cart(valuationItems));
      }

      // endowment manager.
      List<IEndowmentConfig> eConfigs = aConfig.getEConfig();
      for (IEndowmentConfig eConfig : eConfigs) {
        List<String> itemNames = eConfig.getItemNames();
        List<IItem> endowmentItems = new LinkedList<IItem>();
        eConfig.getItemNames()
            .forEach(item -> endowmentItems.add(itemManager.getItems(item)));
        endowmentManager.createEndowment(eConfig.getDistribution(),
            eConfig.getGeneratorConstructors(), eConfig.getGeneratorParams(),
            new Cart(endowmentItems));
      }

      // for the market manager, gonna need the rules, map, and the mustInclude
      // endowments also need a tradeable map, and a tradeable config.
      List<IItem> allItems = new LinkedList<IItem>();
      itemConfig.forEach(iConfig -> allItems
          .add(new Item(iConfig.getItemName(), iConfig.getNumItems())));
      ICart itemCart = new Cart(allItems);
      
      for (List<IMarketConfig> mConfigList : aConfig.getMConfig()) {
        List<IFlexibleRules> marketRules = new LinkedList<IFlexibleRules>();
        List<List<String>> marketItems = new LinkedList<List<String>>();
        for (IMarketConfig mConfig : mConfigList) {
          marketRules.add(mConfig.getRules());
          marketItems.add(mConfig.getTradeableNames());
        }
        List<ICart> marketCarts = new LinkedList<ICart>();
        for (List<String> singleMarketItemNames : marketItems) {
          List<IItem> singleMarketItems = new LinkedList<IItem>();
          singleMarketItemNames.forEach(itemName -> singleMarketItems
              .add(itemCart.getItemByName(itemName)));
          marketCarts.add(new Cart(singleMarketItems));
        }
        marketManager.createSimultaneousMarket(marketRules, marketCarts);
      }

      domainManager.createDomain(itemManager, valuationManager,
          endowmentManager, accountManager);
      worldManager.createWorld(domainManager, marketManager);
      simulationManager.createSimulation(aConfig.getSimulationRuns(), aConfig.getGroupSize(),
          worldManager);
      itemManager.lock();
      valuationManager.lock();
      simulationManager.lock();
      marketManager.lock();
    }

    simulationManager.runSimulation(startingDelayTime, simulationDelayTime, learningDelayTime, 
        numSimulations, serverPort, simulationJsonFileName);
    
  }
}
