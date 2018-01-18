package brown.server.library; 

import java.util.HashMap;
import java.util.List;

import brown.market.library.Market;
import brown.market.marketstate.library.CompleteState;
import brown.market.preset.AbsMarketPreset;
import brown.server.AbsServer;
import brown.setup.ISetup;
import brown.setup.Logging;
import brown.tradeable.ITradeable;
import brown.value.config.AbsValueConfig;

public class RunServer extends AbsServer {

  public RunServer(int port, ISetup gameSetup) {
    super(port, gameSetup);
  }
  
  //what is amt?
  private void delay(int amt) {
    int i = 0;
    while (i < amt) {
      try {
        Thread.sleep(1000);
        Logging.log("[-] pause phase " + i++);
      } catch (InterruptedException e) {
        Logging.log("[+] woken: " + e.getMessage());
      }
    }
  }
  
  //initialGoods is a List of ITradeables; in other places we use Set; consider standardizing
  public void runGame(List<AbsMarketPreset> presets, List<AbsValueConfig> valueInfo, 
      Double initialMonies, List<ITradeable> initialGoods) 
          throws InterruptedException {
    
    //valuations
    this.valueConfig = new HashMap<Integer, AbsValueConfig>(); 
    
    //endowments
    this.initialMonies = initialMonies; 
    this.initialGoods = initialGoods; 
    
    //rules
    for (AbsMarketPreset ruleSet : presets) {
      this.manager.open(new Market(presets.get(presets.indexOf(ruleSet)),
          new CompleteState(0, valueInfo.get(presets.indexOf(ruleSet)).allGoods)));
      //matches corresponding rule sets and value info sets.
      this.valueConfig.put(presets.indexOf(ruleSet), valueInfo.get(presets.indexOf(ruleSet)));
    }
    
    delay(5);//??
    
    this.completeAuction(0);
  } 
  
}