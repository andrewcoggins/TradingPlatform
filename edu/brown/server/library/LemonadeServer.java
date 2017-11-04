package brown.server.library;

import java.util.HashSet;

import brown.marketinternalstates.SimpleInternalState;
import brown.markets.Market;
import brown.markets.presets.LemonadeRules;
import brown.server.TradingServer;
import brown.setup.Logging;
import brown.setup.Setup;
import brown.setup.library.SimpleSetup;
import brown.tradeables.Asset;
import brown.valuable.library.Tradeable;

/**
 * this needs help.
 * @author andrew
 *
 */
public class LemonadeServer extends TradingServer {

  public LemonadeServer(int port, Setup gameSetup) {
    super(port, gameSetup);
    // TODO Auto-generated constructor stub
  }
  
  private void delay(int amt, boolean update) {
    int i = 0;
    while (i < amt) {
      try {
        if (update) {
          this.updateAllAuctions(true);
        }
        Thread.sleep(1000);
        Logging.log("[-] pause phase " + i++);
      } catch (InterruptedException e) {
        Logging.log("[+] woken: " + e.getMessage());
      }
    }
  }
  
  public void runGame() {
  //  delay(10, false);
   // for (int i = 0; i < 5; i++) {
    Asset a = new Asset(new Tradeable(0), 1);
      this.manager.open(new Market(new LemonadeRules(), new SimpleInternalState(0, new HashSet<Asset>())));
      delay(10, true);
   // }
  }
  
  public static void main(String[] args) {
    new LemonadeServer(4242, new SimpleSetup()).runGame();
  }
  
  
}