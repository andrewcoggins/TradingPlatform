package brown.platform.managers.library;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;

import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IInformationRequestMessage;
import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
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
import brown.system.setup.library.SimpleSetup;

public class SimulationManager implements ISimulationManager {

    private List<ISimulation> simulations;
    private List<Integer> numSimulationRuns;  
    private boolean lock;

    private Map<Connection, Integer> agentConnections; 
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
        this.agentConnections = new HashMap<Connection, Integer>(); 
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
    
    public Integer handleRegistration(IRegistrationMessage registrationMessage, Connection connection) {
      Integer theID = registrationMessage.getAgentID();
      Collection<Integer> allIds = this.agentConnections.values();
      if (!allIds.contains(theID)) {
        theID = new Integer((int) (Math.random() * 1000000000));
        while (allIds.contains(theID)) {
          theID = new Integer((int) (Math.random() * 1000000000));
        }
        privateToPublic.put(theID, agentCount++);
        this.agentConnections.put(connection, theID);
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
    
    public IInformationMessage handleInformationRequest(IInformationRequestMessage informationRequest) {
      return this.currentMarketManager.handleInformationRequest(informationRequest); 
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
              this.completeAuctions(); 
            }
            // TODO: log to output  
          }
        }
      }
    
    private void startMessageServer() {
      this.messageServer = new MessageServer(2121, new SimpleSetup(), this);
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
        // send bank update. 
        // send information message with valuation stuff. 
      }
      // give the agents endowments
      // give/send the agents valuations. 
    }

    private void completeAuctions() throws InterruptedException {
      while (this.currentMarketManager.anyMarketsOpen()) {
        // TODO: make this a platform parameter
        Thread.sleep(1000);
        updateAuctions(); 
      }
    }
    
    private void updateAuctions() {
      for (IMarket market : this.currentMarketManager.getOpenMarkets()) {
        if (market.isOpen()) {
          List<ITradeRequestMessage> tradeRequests = this.currentMarketManager.updateMarket(market.getMarketID(), new LinkedList<Integer>(this.agentConnections.values())); 
          for (ITradeRequestMessage tradeRequest : tradeRequests) {
            this.messageServer.sendTradeRequest(tradeRequest);
            // TODO: maybe send an information message? 
          }
        } else {
          List<IAccountUpdate> accountUpdates = this.currentMarketManager.finishMarket(market.getMarketID());
          // TODO: update accounts
          // TODO: send bank updates. 
        }
      }
    }
   }

