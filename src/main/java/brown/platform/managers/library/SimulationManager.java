package brown.platform.managers.library;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.valuation.ISpecificValuation;
import brown.auction.value.valuation.library.GeneralValuation;
import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.communication.messageserver.IMessageServer;
import brown.communication.messageserver.library.MessageServer;
import brown.logging.library.PlatformLogging;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.IInitialEndowment;
import brown.platform.item.IItem;
import brown.platform.item.library.Item;
import brown.platform.managers.IAccountManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.IMarketManager;
import brown.platform.managers.ISimulationManager;
import brown.platform.managers.IValuationManager;
import brown.platform.managers.IWorldManager;
import brown.platform.simulation.ISimulation;
import brown.platform.simulation.library.Simulation;
import brown.system.setup.library.Startup;

public class SimulationManager implements ISimulationManager {
  
  private final int INTERVAL = 1000; 
  private final int IDMULTIPLIER = 1000000000; 
  
  private List<ISimulation> simulations;
  private List<Integer> numSimulationRuns;
  private boolean lock;

  private Map<Integer, Connection> agentConnections;
  private Map<Integer, Integer> privateToPublic;
  private Map<Integer, String> idToName;
  private int agentCount;

  private IMarketManager currentMarketManager;
  private IAccountManager currentAccountManager;
  private IEndowmentManager currentEndowmentManager;
  private IValuationManager currentValuationManager;

  private IMessageServer messageServer;

  public SimulationManager() {
    this.simulations = new LinkedList<>();
    this.lock = false;
    this.numSimulationRuns = new LinkedList<Integer>();

    this.privateToPublic = new HashMap<Integer, Integer>();
    this.agentConnections = new HashMap<Integer, Connection>();
    this.idToName = new HashMap<Integer, String>();
    this.agentCount = 0;
  }

  @Override
  public void createSimulation(int numSimulationRuns,
      IWorldManager worldManager) {
    if (!this.lock) {
      this.simulations.add(new Simulation(worldManager));
      this.numSimulationRuns.add(numSimulationRuns);
    } else {
      PlatformLogging.log("Creation denied: simulation manager locked.");
    }
  }

  @Override
  public void lock() {
    this.lock = true;
  }

  @Override
  public void runSimulation(int startingDelayTime, int simulationDelayTime,
      int numRuns) throws InterruptedException {
    startMessageServer();
    Thread.sleep(startingDelayTime * INTERVAL);
    for (int i = 0; i < numRuns; i++) {
      for (int j = 0; j < this.simulations.size(); j++) {

        this.currentMarketManager = this.simulations.get(j).getWorldManager()
            .getWorld().getMarketManager();
        this.currentAccountManager = this.simulations.get(j).getWorldManager()
            .getWorld().getDomainManager().getDomain().getAccountManager();
        this.currentEndowmentManager = this.simulations.get(j).getWorldManager()
            .getWorld().getDomainManager().getDomain().getEndowmentManager();
        this.currentValuationManager = this.simulations.get(j).getWorldManager()
            .getWorld().getDomainManager().getDomain().getValuationManager();

        for (int k = 0; k < this.numSimulationRuns.get(k); k++) {
          this.initializeAgents();
          for (int l = 0; l < this.currentMarketManager
              .getNumMarketBlocks(); l++) {
            this.runAuction(simulationDelayTime, l);
          }
          // TODO: log to output
          // reset
          this.currentMarketManager.reset();
          this.currentAccountManager.reset();
          this.currentValuationManager.reset();
          // no need for endowment manager, since it's stateless
        }
      } 
    }
  }

  @Override
  public Integer handleRegistration(IRegistrationMessage registrationMessage,
      Connection connection) {
    Integer theID = -1;
    Collection<Integer> allIds = this.agentConnections.keySet();
    if (!allIds.contains(theID)) {
      theID = new Integer((int) (Math.random() * IDMULTIPLIER));
      while (allIds.contains(theID)) {
        theID = new Integer((int) (Math.random() * IDMULTIPLIER));
      }
      privateToPublic.put(theID, agentCount++);
      this.agentConnections.put(theID, connection);
      if (registrationMessage.getName() != null) {
        this.idToName.put(theID, registrationMessage.getName());
      } else {
        PlatformLogging.log(
            "[x] AbsServer-onRegistration: encountered registration from existing agent");
      }
      PlatformLogging.log("[-] registered " + theID);
      connection.sendTCP(15000);
      connection.setTimeout(60000);
      return theID;
    }
    return -1;
  }

  @Override
  public void giveTradeMessage(ITradeMessage tradeMessage) {
    // TODO: send back a status message
    this.currentMarketManager.handleTradeMessage(tradeMessage);
  }
  
  private synchronized void runAuction(int simulationDelayTime, int index)
      throws InterruptedException {
    this.currentMarketManager.openMarkets(index);
    while (this.currentMarketManager.anyMarketsOpen()) {
      Thread.sleep(simulationDelayTime * INTERVAL);
      updateAuctions();
      Thread.sleep(simulationDelayTime * INTERVAL);
    }
  }

  private void updateAuctions() {
    for (Integer marketID : this.currentMarketManager.getActiveMarketIDs()) {
      // we still need to synchronize on the market for this whole operation. or maybe can pare it down to MM methods? 
      synchronized (this.currentMarketManager.getActiveMarket(marketID)) {
        if (this.currentMarketManager.marketOpen(marketID)) {
          List<ITradeRequestMessage> tradeRequests =
              this.currentMarketManager.updateMarket(marketID,
                  new LinkedList<Integer>(this.agentConnections.keySet()));
          for (ITradeRequestMessage tradeRequest : tradeRequests) {
            this.messageServer.sendMessage(
                this.agentConnections.get(tradeRequest.getAgentID()),
                tradeRequest);
          }
        } else {
          List<IAccountUpdate> accountUpdates =
              this.currentMarketManager.finishMarket(marketID);
          this.currentAccountManager.updateAccounts(accountUpdates);
          Map<Integer, IBankUpdateMessage> bankUpdates =
              this.currentAccountManager
                  .constructBankUpdateMessages(accountUpdates);
          Map<Integer, IInformationMessage> informationMessages =
              this.currentMarketManager.constructInformationMessages(marketID, 
                      new LinkedList<Integer>(this.agentConnections.keySet()));
          for (Integer agentID : bankUpdates.keySet()) {
            this.messageServer.sendMessage(this.agentConnections.get(agentID),
                informationMessages.get(agentID));
            this.messageServer.sendMessage(this.agentConnections.get(agentID),
                bankUpdates.get(agentID));
          }
          this.currentMarketManager.finalizeMarket(marketID);
        }
      }
    };
  }

  private void initializeAgents() { 
    for (Integer agentID : privateToPublic.keySet()) {
      // give agent endowment, and create account. 
      IInitialEndowment agentEndowment =
          this.currentEndowmentManager.getEndowment();
      if (this.currentAccountManager.containsAccount(agentID)) {
        this.currentAccountManager.reendow(agentID, agentEndowment);
      } else {
        this.currentAccountManager.createAccount(agentID, agentEndowment);
      }
      // give agent valuation
      Map<List<IItem>, ISpecificValuation> specificValuationMap = new HashMap<List<IItem>, ISpecificValuation>(); 
      for (IValuationDistribution specificDistribution : this.currentValuationManager.getDistribution()) { 
        List<IItem> specificItems = new LinkedList<IItem>(); 
        for (String itemName : specificDistribution.getItemNames()) {
          specificItems.add(new Item(itemName)); 
        }
        specificValuationMap.put(specificItems, specificDistribution.sample()); 
      }
      this.currentValuationManager.addAgentValuation(agentID, new GeneralValuation(specificValuationMap));
    }
    // the account manager should be able to create these messages.
    Map<Integer, IBankUpdateMessage> accountInitializations =
        this.currentAccountManager.constructInitializationMessages();
    Map<Integer, IValuationMessage> agentValuations =
        this.currentValuationManager.constructValuationMessages();
    for (Integer agentID : accountInitializations.keySet()) {
      this.messageServer.sendMessage(this.agentConnections.get(agentID),
          accountInitializations.get(agentID));
      this.messageServer.sendMessage(this.agentConnections.get(agentID),
          agentValuations.get(agentID));
    }
  }
  
  private void startMessageServer() {
    this.messageServer = new MessageServer(2121, new Startup(), this);
  }

}
