package brown.server.library;

import brown.markets.Market;
import brown.markets.presets.LemonadeRules;
import brown.server.TradingServer;
import brown.setup.Logging;
import brown.setup.Setup;
import brown.setup.library.SimpleSetup;

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
    delay(10, false);
    for (int i = 0; i < 5; i++) {
      this.manager.open(new Market(new LemonadeRules()));
      delay(5, true);
    }
  }
  
  public static void main(String[] args) {
    new LemonadeServer(2121, new SimpleSetup()).runGame();
  }
  
  
}