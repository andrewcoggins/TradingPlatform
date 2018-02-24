package brown.server.library;

import java.util.LinkedList;
import java.util.List;
import brown.logging.Logging;
import brown.market.preset.AbsMarketPreset;
import brown.server.AbsServer;
import brown.setup.ISetup;
import brown.summary.AuctionSummarizer;
import brown.tradeable.ITradeable;
import brown.value.config.ValConfig;

public class RunServer extends AbsServer{

  public RunServer(int port, ISetup gameSetup) {
    super(port, gameSetup);
  }
  
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
      ValConfig valInfo, double initialMonies, List<ITradeable> initialGoods, int delay, int lag) throws InterruptedException {
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
    printUtilities();
  }
  
  /**
   * Runs a series of simulations.=
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
  public void runSimulation(Simulation sim, int numRuns, int delay,int lag) throws InterruptedException {
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
      // this.summarizer.collectInformation(this.acctManager.getAccounts(), this.privateValuations);
      resetSim();
      count++;
    }
    printUtilities();
  } 
}
