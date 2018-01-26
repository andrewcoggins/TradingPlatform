package brown.server.library;

import java.util.LinkedList;
import java.util.List;

import brown.market.preset.AbsMarketPreset;
import brown.server.AbsServer;
import brown.setup.ISetup;
import brown.setup.Logging;
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

  public void runSimpleSim(List<ITradeable> allGoods, AbsMarketPreset rules,
      ValConfig valInfo, double initialMonies, List<ITradeable> initialGoods, int delay, int lag) throws InterruptedException{
    this.valueConfig = valInfo;
    this.allTradeables = allGoods;
    this.initialMonies = initialMonies;
    this.initialGoods = initialGoods;   
    delay(delay);
    initializeAgents();   
    this.manager.open(rules,0,allGoods,new LinkedList<Integer>(this.connections.values()));   
    this.completeAuctions(lag);
    printUtilities();
  }
  
  // need to do something with valuation calculating utilities
  // need to do something here about setting groupings
  public void runSimulation(Simulation sim, int numRuns, int delay) throws InterruptedException {
    //tradeables
    this.allTradeables = sim.getTradeables();
    //valuations
    this.valueConfig = sim.getValInfo();     
    //endowments
    this.initialMonies = sim.getInitialMonies(); 
    this.initialGoods = sim.getInitialGoods(); 
    
    // time for agents to register (Make registration happen here)
    delay(delay);    
    
    int count = 0;
    while (count < numRuns) {
      initializeAgents();
      for (SimulMarkets s : sim.getSequence()){
        // Open markets
        runGame(sim.getTradeables(),s.getMarkets());        
      }        
      resetSim();
      count++;
    }
  } 
  
  public void runGame(List<ITradeable> allGoods, List<AbsMarketPreset> presets) throws InterruptedException {        
    //rules
    for (AbsMarketPreset ruleSet : presets) {
      int id = 0;
      this.manager.open(ruleSet, new Integer(id), allGoods,new LinkedList<Integer>(this.connections.values()));
    }    
    this.completeAuctions(1000);    
  }
}
