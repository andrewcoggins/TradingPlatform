package brown.platform.managers.library;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.esotericsoftware.kryonet.Connection;

import brown.auction.endowment.IEndowment;
import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.valuation.IGeneralValuation;
import brown.auction.value.valuation.ISpecificValuation;
import brown.auction.value.valuation.library.GeneralValuation;
import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.communication.messageserver.IMessageServer;
import brown.communication.messageserver.library.MessageServer;
import brown.logging.library.PlatformLogging;
import brown.platform.accounting.IAccount;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.item.IItem;
import brown.platform.item.library.Item;
import brown.platform.managers.IAccountManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.IMarketManager;
import brown.platform.managers.ISimulationManager;
import brown.platform.managers.IUtilityManager;
import brown.platform.managers.IValuationManager;
import brown.platform.managers.IWorldManager;
import brown.platform.simulation.ISimulation;
import brown.platform.simulation.library.Simulation;
import brown.system.setup.library.Setup;

/**
 * SimulationManager creates and stores ISimulation. simulation runs within the
 * simulation manager.
 * 
 * @author andrewcoggins
 *
 */
public class SimulationManager implements ISimulationManager {

  private final int MILLISECONDS = 1000;
  private final int IDMULTIPLIER = 1000000000;
  
  private int serverPort; 
  private List<ISimulation> simulations;
  private List<Integer> numSimulationRuns;
  private boolean lock;

  private Map<Integer, Connection> agentConnections;
  private Map<Integer, Integer> privateToPublic;
  private Map<Integer, String> idToName;
  private List<List<Integer>> agentGroups;
  private int agentCount;

  private IMarketManager currentMarketManager;
  private IAccountManager currentAccountManager;
  private IEndowmentManager currentEndowmentManager;
  private IValuationManager currentValuationManager;
  private IUtilityManager utilityManager;
  private int groupSize;

  private IMessageServer messageServer;

  public SimulationManager() {
    this.simulations = new LinkedList<>();
    this.lock = false;
    this.numSimulationRuns = new LinkedList<Integer>();

    this.privateToPublic = new HashMap<Integer, Integer>();
    this.agentConnections = new HashMap<Integer, Connection>();
    this.idToName = new HashMap<Integer, String>();
    this.utilityManager = new UtilityManager();
    this.agentCount = 0;
  }

  @Override
  public void createSimulation(int numSimulationRuns, int groupSize,
      IWorldManager worldManager) {
    if (!this.lock) {
      this.simulations.add(new Simulation(groupSize, worldManager));
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
  public void runSimulation(int startingDelayTime, double simulationDelayTime,
      int numRuns, int serverPort) throws InterruptedException {
    this.serverPort = serverPort; 
    startMessageServer();
    PlatformLogging.log("Agent connection phase: sleeping for "
        + startingDelayTime + " seconds");
    for (int i = 0; i < startingDelayTime; i++) {
      PlatformLogging.log(startingDelayTime - i + " seconds left to register");
      Thread.sleep(MILLISECONDS);
    }
    PlatformLogging.log("Agent connection phase: beginning simulation");
    // add the agent IDs to the utility manager.
    // should this be here, or in handleRegistration?
    this.privateToPublic.keySet()
        .forEach(id -> this.utilityManager.addAgentRecord(id));
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
        this.groupSize = this.simulations.get(j).getGroupSize();
        this.setAgentGroupings();
        
        for (int k = 0; k < this.numSimulationRuns.get(j); k++) {
          this.initializeAgents();
          for (int l = 0; l < this.currentMarketManager
              .getNumMarketBlocks(); l++) {
            PlatformLogging.log("running simulation");
            this.runAuction(simulationDelayTime, l);
          }
          // after simulation is over, send simulation report messages
          sendSimulationReportMessages();
          // update utility totals.
          Map<Integer, IGeneralValuation> agentValuations =
              new HashMap<Integer, IGeneralValuation>();
          Map<Integer, IAccount> agentAccounts =
              new HashMap<Integer, IAccount>();
          this.privateToPublic.keySet().forEach(key -> agentValuations.put(key,
              this.currentValuationManager.getAgentValuation(key)));
          this.privateToPublic.keySet().forEach(key -> agentAccounts.put(key,
              this.currentAccountManager.getAccount(key)));
          this.utilityManager.updateUtility(agentAccounts, agentValuations);
          // reset managers.
          this.currentMarketManager.reset();
          this.currentAccountManager.reset();
          this.currentValuationManager.reset();
          this.currentEndowmentManager.reset();
        }
      }
    }
    this.messageServer.stopMessageServer();
    this.utilityManager.logFinalUtility("", this.privateToPublic,
        this.idToName);
  }

  @Override
  public Integer handleRegistration(IRegistrationMessage registrationMessage,
      Connection connection) {
    Integer agentPrivateID = -1;
    Collection<Integer> allIds = this.agentConnections.keySet();
    if (!allIds.contains(agentPrivateID)) {
      agentPrivateID = ((int) (Math.random() * IDMULTIPLIER));
      while (allIds.contains(agentPrivateID)) {
        agentPrivateID = ((int) (Math.random() * IDMULTIPLIER));
      }
      privateToPublic.put(agentPrivateID, agentCount++);
      this.agentConnections.put(agentPrivateID, connection);
      if (registrationMessage.getName() != null) {
        this.idToName.put(agentPrivateID, registrationMessage.getName());
      } else {
        PlatformLogging.log(
            "[x] AbsServer-onRegistration: encountered registration from existing agent");
      }
      PlatformLogging.log("[-] registered " + agentPrivateID);
      connection.sendTCP(15000);
      connection.setTimeout(60000);
      return agentPrivateID;
    }
    return -1;
  }

  @Override
  public void giveTradeMessage(ITradeMessage tradeMessage) {
    // TODO: send back a status message
    this.currentMarketManager.handleTradeMessage(tradeMessage);
  }

  private synchronized void runAuction(double simulationDelayTime, int index)
      throws InterruptedException {
    PlatformLogging.log(this.agentGroups.size() + " Agent Groups"); 
    PlatformLogging.log(this.agentGroups);
    for (int i = 0; i < this.agentGroups.size(); i++) {
      this.currentMarketManager.openMarkets(index, new HashSet<Integer>(agentGroups.get(i)), i, this.agentGroups.size()); 
    }
    while (this.currentMarketManager.anyMarketsOpen()) {
      Thread.sleep((int) (simulationDelayTime * MILLISECONDS));
      PlatformLogging.log("updating auctions");

      updateAuctions();
      Thread.sleep((int) (simulationDelayTime * MILLISECONDS));
    }
    updateAuctions();
  }

  private void updateAuctions() {
    for (Integer marketID : this.currentMarketManager.getActiveMarketIDs()) {
      // we still need to synchronize on the market for this whole operation. or
      // maybe can pare it down to MM methods?
      synchronized (this.currentMarketManager.getActiveMarket(marketID)) {
        if (this.currentMarketManager.marketOpen(marketID)) {
          // updating the market.
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
    }
  }

  private void sendSimulationReportMessages() {
    Map<Integer, ISimulationReportMessage> simReportMessages =
        this.currentMarketManager.constructSimulationReportMessages(
            new LinkedList<Integer>(this.privateToPublic.keySet()));
    for (Integer agentID : this.privateToPublic.keySet()) {
      this.messageServer.sendMessage(this.agentConnections.get(agentID),
          simReportMessages.get(agentID));
    }
  }

  private void initializeAgents() {
    for (Integer agentID : privateToPublic.keySet()) {
      // give agent endowment, and create account.
      IEndowment agentEndowment =
          this.currentEndowmentManager.makeAgentEndowment(agentID);
      if (this.currentAccountManager.containsAccount(agentID)) {
        this.currentAccountManager.reendow(agentID, agentEndowment);
      } else {
        this.currentAccountManager.createAccount(agentID, agentEndowment);
      }
      // give agent valuation
      Map<List<IItem>, ISpecificValuation> specificValuationMap =
          new HashMap<List<IItem>, ISpecificValuation>();
      for (IValuationDistribution specificDistribution : this.currentValuationManager
          .getDistribution()) {
        List<IItem> specificItems = new LinkedList<IItem>();
        for (String itemName : specificDistribution.getItemNames()) {
          specificItems.add(new Item(itemName));
        }
        specificValuationMap.put(specificItems, specificDistribution.sample());
      }

      this.currentValuationManager.addAgentValuation(agentID,
          new GeneralValuation(specificValuationMap));
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

  private void setAgentGroupings() {
    this.agentGroups = new LinkedList<List<Integer>>();
    if (this.groupSize > 0) {
      for (Integer agentID : privateToPublic.keySet()) {
        List<List<Integer>> incompleteAgentGroups =
            this.agentGroups.stream().filter(list -> list.size() < this.groupSize)
                .collect(Collectors.toList());
        if (incompleteAgentGroups.size() > 0) {
          List<Integer> incompleteGroup = incompleteAgentGroups.get(0); 
          this.agentGroups.remove(incompleteGroup); 
          incompleteGroup.add(agentID); 
          this.agentGroups.add(incompleteGroup); 
        } else {
          List<Integer> incompleteGroup = new LinkedList<Integer>(); 
          incompleteGroup.add(agentID);
          this.agentGroups.add(incompleteGroup); 
        }
      } 
    } else {
      List<Integer> agentGroup = new LinkedList<Integer>(); 
      for (Integer agentID : privateToPublic.keySet()) {
        agentGroup.add(agentID);
      }
      this.agentGroups.add(agentGroup); 
    }
  }
  
  @Override
  public Map<Integer, Integer> getAgentIDs() {
    return this.privateToPublic;
  }
  
  private void startMessageServer() {
    this.messageServer = new MessageServer(this.serverPort, new Setup(), this);
  }

}
