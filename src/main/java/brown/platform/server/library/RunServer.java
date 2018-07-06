package brown.platform.server.library;

import java.util.LinkedList;
import java.util.List;

import brown.auction.preset.AbsMarketPreset;
import brown.auction.value.config.library.ValConfig;
import brown.logging.library.Logging;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.server.IServer;
import brown.platform.summary.library.AuctionSummarizer;
import brown.system.setup.ISetup;

/**
 * RunServer manages some of the dynamics of running a simulation, 
 * including how many times a simulation will be run, the delay between auctions, 
 * and the sequence of markets in a single simulation.
 * @author acoggins
 *
 */
public class RunServer extends TradingServer implements IServer {
  
  /**
   * runServer simple extends AbsServer
   * @param port
   * @param gameSetup
   */
  public RunServer(int port, ISetup gameSetup) {
    super(port, gameSetup);
  }
  
  /**
   * creates a delay for agents to register at the beginning of a simulation.
   * @param time
   */
  private void delay(int time) {
    int i = 0;
    while (i < time) {
      try {
        Thread.sleep(1000);
        Logging.log("[-] pause phase " + i++);
      } catch (InterruptedException e) {
        Logging.log("[+] woken: " + e.getMessage());
      }
    }
  }

  /**
   * run a simple simulation with only one market.
   * @param allGoods
   * all goods to be traded on. 
   * @param rules
   * rules of the market to be traded in.
   * @param valInfo
   * vlauation info related to the market.
   * @param initialMonies
   * initial money for the agents.
   * @param initialGoods
   * initial goods for the agents.
   * @param delay
   * delay time between trading rounds
   * @param lag
   * lag time for registration phase.
   * @throws InterruptedException
   */
  public void runSimpleSim(List<ITradeable> allGoods, AbsMarketPreset rules,
      ValConfig valInfo, double initialMonies, List<ITradeable> initialGoods, int delay, int lag, String outputFile) throws InterruptedException {
    this.valueConfig = valInfo;
    this.allTradeables = allGoods;
    this.initialMonies = initialMonies;
    this.initialGoods = initialGoods;   
    delay(delay);
    initializeAgents();  
    List<AbsMarketPreset> amp = new LinkedList<AbsMarketPreset>();
    amp.add(rules);
    SimulMarkets s = new SimulMarkets(amp);
    this.manager.addSimulMarket(s, allGoods, new LinkedList<Integer>(this.connections.values())); 
    this.summarizer = new AuctionSummarizer(this.privateToPublic.keySet());
    this.completeAuctions(lag);
    this.summarizer.collectInformation(this.acctManager.getAccounts(), this.privateValuations);
    printUtilities(outputFile);
  }
  
  /**
   * Runs a series of simulations.
   * @param sim
   * the simluation to be run.  
   * @param numRuns
   * number of runs for the simulation.
   * @param delay
   * delay time between trading rounds.
   * @param lag
   * lag time for registration phase.
   * @throws InterruptedException
   */
  public void runSimulation(Simulation sim, int numRuns, int delay, int initLag, int lag, String outputFile) throws InterruptedException {
    this.valueConfig = sim.getValInfo();         
    this.allTradeables = sim.getTradeables();
    this.initialMonies = sim.getInitialMonies(); 
    this.initialGoods = sim.getInitialGoods(); 
    delay(delay);       
    this.summarizer = new AuctionSummarizer(this.privateToPublic.keySet());
    int count = 0;
    while (count < numRuns) {      
      initializeAgents();   
      Logging.log("Entering Initial Sleep");
      Thread.sleep(initLag);
      Logging.log("Ending Initial Sleep");      
      for (SimulMarkets s : sim.getSequence()) {
        this.manager.addSimulMarket(s, sim.getTradeables(), new LinkedList<Integer>(this.connections.values()));
        this.completeAuctions(lag);    
      } 
      this.summarizer.collectInformation(this.acctManager.getAccounts(), this.privateValuations);
      resetSim();
      count++;
    }
    printUtilities(outputFile);
    this.kryoServer.close();
    this.kryoServer.stop();
  } 
  
  public void runSimulation(Simulation sim, int numRuns, int delay, int lag, String outputFile) throws InterruptedException {
    this.valueConfig = sim.getValInfo();         
    this.allTradeables = sim.getTradeables();
    this.initialMonies = sim.getInitialMonies(); 
    this.initialGoods = sim.getInitialGoods(); 
    delay(delay);       
    this.summarizer = new AuctionSummarizer(this.privateToPublic.keySet());
    int count = 0;
    while (count < numRuns) {
      initializeAgents();                   
      for (SimulMarkets s : sim.getSequence()) {
        this.manager.addSimulMarket(s, sim.getTradeables(), new LinkedList<Integer>(this.connections.values()));
        this.completeAuctions(lag);    
      } 
      this.summarizer.collectInformation(this.acctManager.getAccounts(), this.privateValuations);
      resetSim();
      count++;
    }
    printUtilities(outputFile);
    this.kryoServer.close();
    this.kryoServer.stop();
  } 
}
