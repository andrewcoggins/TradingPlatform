package brown.platform.managers.library;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;

import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.communication.messageserver.IMessageServer;
import brown.communication.messageserver.library.MessageServer;
import brown.logging.library.Logging;
import brown.logging.library.PlatformLogging;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.IInitialEndowment;
import brown.platform.managers.IAccountManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.IMarketManager;
import brown.platform.managers.ISimulationManager;
import brown.platform.managers.IValuationManager;
import brown.platform.managers.IWorldManager;
import brown.platform.market.IMarket;
import brown.platform.simulation.ISimulation;
import brown.platform.simulation.library.Simulation;
import brown.platform.world.library.Domain;
import brown.platform.world.library.World;
import brown.system.setup.library.Startup;

public class SimulationManager implements ISimulationManager {

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

    public void createSimulation(int numSimulationRuns, IWorldManager worldManager) {
        if (!this.lock) {
            this.simulations.add(new Simulation(worldManager));
            this.numSimulationRuns.add(numSimulationRuns); 
        } else {
            PlatformLogging.log("Creation denied: simulation manager locked.");
        }
    }

    public void lock() {
        this.lock = true;
    }
    
    private void startMessageServer() {
      this.messageServer = new MessageServer(2121, new Startup(), this);
    }
    
    public void runSimulation(int waitTime, int numRuns) throws InterruptedException {
      startMessageServer(); 
      Thread.sleep(waitTime * 1000);
        for(int j = 0; j < this.simulations.size(); j++) {

          this.currentMarketManager = ((World) ((Simulation) this.simulations.get(j)).worldManager.getWorld()).market; 
          this.currentAccountManager = ((Domain) ((World) ((Simulation) this.simulations.get(j)).worldManager.getWorld()).domain).acctManager;
          this.currentEndowmentManager = ((Domain) ((World) ((Simulation) this.simulations.get(j)).worldManager.getWorld()).domain).endowmentManager; 
          this.currentValuationManager = ((Domain) ((World) ((Simulation) this.simulations.get(j)).worldManager.getWorld()).domain).valuation; 
          
          for (int k = 0; k < this.numSimulationRuns.get(k); j++) {
            this.initializeAgents();
            for (int l = 0; l < this.currentMarketManager.getNumMarketBlocks(); l++) {
              this.completeAuctions(l); 
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

    private synchronized void completeAuctions(int index) throws InterruptedException {
      this.currentMarketManager.openMarkets(index); 
      while (this.currentMarketManager.anyMarketsOpen()) {
        Thread.sleep(1000);
        updateAuctions(); 
        Thread.sleep(1000);
      }
    }
    
    private void updateAuctions() {
      for (IMarket market : this.currentMarketManager.getActiveMarkets()) {
        synchronized(market) {
          if (market.isOpen()) {
            List<ITradeRequestMessage> tradeRequests = this.currentMarketManager.updateMarket(market.getMarketID(), new LinkedList<Integer>(this.agentConnections.keySet())); 
            for (ITradeRequestMessage tradeRequest : tradeRequests) {
              // TODO: send an (inner) information message 
              this.messageServer.sendMessage(this.agentConnections.get(tradeRequest.getAgentID()), tradeRequest);
            }
          } else {
            List<IAccountUpdate> accountUpdates = this.currentMarketManager.finishMarket(market.getMarketID());
            this.currentAccountManager.updateAccounts(accountUpdates); 
            Map<Integer, IBankUpdateMessage> bankUpdates = this.currentAccountManager.constructBankUpdateMessages(accountUpdates); 
            Map<Integer, IInformationMessage> informationMessages = this.currentMarketManager.constructInformationMessages(market.getMarketID()); 
            for (Integer agentID : bankUpdates.keySet()) {
              this.messageServer.sendMessage(this.agentConnections.get(agentID), informationMessages.get(agentID));
              this.messageServer.sendMessage(this.agentConnections.get(agentID), bankUpdates.get(agentID));
            }
            this.currentMarketManager.finalizeMarket(market.getMarketID()); 
          }
        }
      }
    }
    
    private void initializeAgents() {
      // give the agent accounts 
      for (Integer agentID : privateToPublic.keySet()) {
        IInitialEndowment agentEndowment = this.currentEndowmentManager.getEndowment(); 
        if (this.currentAccountManager.containsAccount(agentID)) {
          this.currentAccountManager.reendow(agentID, agentEndowment);
        } else {
          this.currentAccountManager.createAccount(agentID, agentEndowment);
        }
        
        // TODO: give agents valuations
      }
      // the account manager should be able to create these messages. 
      Map<Integer, IBankUpdateMessage> accountInitializations = this.currentAccountManager.constructInitializationMessages(); 
      Map<Integer, IValuationMessage> agentValuations = this.currentValuationManager.constructValuationMessages(); 
      for (Integer agentID : accountInitializations.keySet()) {
        this.messageServer.sendMessage(this.agentConnections.get(agentID), accountInitializations.get(agentID));
        this.messageServer.sendMessage(this.agentConnections.get(agentID), agentValuations.get(agentID));
      }
    }
    
    public Integer handleRegistration(IRegistrationMessage registrationMessage, Connection connection) {
      Integer theID = -1; 
      Collection<Integer> allIds = this.agentConnections.keySet();
      if (!allIds.contains(theID)) {
        theID = new Integer((int) (Math.random() * 1000000000));
        while (allIds.contains(theID)) {
          theID = new Integer((int) (Math.random() * 1000000000));
        }
        privateToPublic.put(theID, agentCount++);
        this.agentConnections.put(theID, connection);
        if (registrationMessage.getName() != null) {
            this.idToName.put(theID, registrationMessage.getName());
        } else {
          Logging.log("[x] AbsServer-onRegistration: encountered registration from existing agent");
        }
        Logging.log("[-] registered " + theID);
        connection.sendTCP(15000);
        connection.setTimeout(60000);
        return theID; 
        }
      return -1; 
      }
    
    public void giveTradeMessage(ITradeMessage tradeMessage) {
      this.currentMarketManager.handleTradeMessage(tradeMessage);
    }
    
   }

