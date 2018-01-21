package brown.server.library;

import java.util.List;

import brown.market.preset.AbsMarketPreset;
import brown.server.AbsServer2;
import brown.setup.ISetup;
import brown.setup.Logging;
import brown.tradeable.ITradeable;
import brown.value.config.ValConfig;

public class RunServer2 extends AbsServer2{

  public RunServer2(int port, ISetup gameSetup) {
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
      ValConfig valInfo, double initialMonies, List<ITradeable> initialGoods) throws InterruptedException{
    this.valueConfig = valInfo;
    this.initialMonies = initialMonies;
    this.initialGoods = initialGoods;
    delay(5);
    initializeAgents();    
    this.manager.open(rules,0,allGoods);    
    this.completeAuctions(1000);
  }
  
  // need to do something with valuation calculating utilities
  public void runSimulation(Simulation sim, int numRuns) throws InterruptedException {
    //valuations
    this.valueConfig = sim.getValInfo();     
    //endowments
    this.initialMonies = sim.getInitialMonies(); 
    this.initialGoods = sim.getInitialGoods(); 
    
    // time for agents to register (Make registration happen here)
    delay(10);    
    
    int count = 0;
    while (count < numRuns){
      initializeAgents();
      for (SimulMarkets s : sim.getSequence()){
        // Open markets
        runGame(sim.getTradeables(),s.getMarkets());        
      }        
      reset();
      count++;
    }
  } 
  
  public void runGame(List<ITradeable> allGoods, List<AbsMarketPreset> presets) throws InterruptedException {        
    //rules
    for (AbsMarketPreset ruleSet : presets) {
      int id = 0;
      this.manager.open(ruleSet, new Integer(id), allGoods);
    }    
    this.completeAuctions(1000);    
  }
}
