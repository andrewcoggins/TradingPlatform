package brown.server.library; 

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import brown.market.library.Market;
import brown.market.marketstate.library.CompleteState;
import brown.market.preset.AbsMarketPreset;
import brown.server.AbsServer;
import brown.setup.ISetup;
import brown.setup.Logging;
import brown.tradeable.library.MultiTradeable;
import brown.value.config.AbsValueConfig;

public class RunServer extends AbsServer {

  public RunServer(int port, ISetup gameSetup) {
    super(port, gameSetup);
  }
  
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
  
  public void runGame(List<AbsMarketPreset> presets, 
      List<AbsValueConfig> valueInfo, Double initialMonies,
      List<MultiTradeable> initialGoods) throws InterruptedException {
    this.valueConfig = new HashMap<Integer, AbsValueConfig>(); 
    this.initialMonies = initialMonies; 
    this.initialGoods = initialGoods; 
    for (AbsMarketPreset ruleSet : presets) {
      this.manager.open(new Market(presets.get(presets.indexOf(ruleSet)),
          new CompleteState(0, valueInfo.get(presets.indexOf(ruleSet)).allGoods)));
      //matches corresponding rule sets and value info sets.
      this.valueConfig.put(presets.indexOf(ruleSet), valueInfo.get(presets.indexOf(ruleSet)));
    }
    delay(5);
    this.completeAuction(0);
  } 
  
}