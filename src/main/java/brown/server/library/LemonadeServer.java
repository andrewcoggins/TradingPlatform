package brown.server.library;

import java.util.HashSet;

import brown.accounting.Account;
import brown.market.library.Market;
import brown.market.marketstate.library.InternalState;
import brown.market.preset.library.LemonadeRules;
import brown.server.AbsServer;
import brown.setup.Logging;
import brown.setup.ISetup;
import brown.setup.library.SimpleSetup;
import brown.tradeable.library.Tradeable;

/**
 * this needs help.
 * @author andrew
 *
 */
public class LemonadeServer extends AbsServer {

  public LemonadeServer(int port, ISetup gameSetup) {
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
  
  public void runGame() throws InterruptedException {
    delay(5);
      this.manager.open(new Market(new LemonadeRules(), new InternalState(0, new HashSet<Tradeable>())));
      this.completeAuction(0);
      for (Account acct : this.acctManager.getAccounts()) {
        Logging.log(acct.toString()); 
      }
  }
  
  public static void main(String[] args) throws InterruptedException {
    new LemonadeServer(2121, new SimpleSetup()).runGame();
  }
}