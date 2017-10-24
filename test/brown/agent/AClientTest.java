package brown.agent;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.esotericsoftware.kryonet.Connection;

import brown.assets.accounting.Account;
import brown.marketinternalstates.SimpleInternalState;
import brown.markets.Market;
import brown.registration.ValuationRegistration;
import brown.rules.allocationrules.library.SimpleHighestBidderAllocation;
import brown.rules.paymentrules.library.SimpleSecondPrice;
import brown.server.TradingServer;
import brown.setup.Logging;
import brown.setup.library.SimpleSetup;
import brown.valuable.library.Tradeable;

/**
 * test the ability of the system to send and receive messages.
 * @author andrew
 *
 */
public class AClientTest extends TradingServer {

  public AClientTest(int port) {
    super(port, new SimpleSetup());
    // TODO Auto-generated constructor stub
  } 
  
  public void run() {
    for(Entry<Connection, Integer> conn : this.connections.entrySet()) { 
      Map<Tradeable, Double> testItem = new HashMap<Tradeable, Double>();
      testItem.put(new Tradeable(999), 1.111);
      this.theServer.sendToTCP(conn.getKey().getID(), new ValuationRegistration(conn.getValue(), testItem));
    }
    Market market = new Market(new SimpleSecondPrice(), new SimpleHighestBidderAllocation(),
        null, null, null, null, new SimpleInternalState(2121, null));
    this.manager.open(market);
    while(!market.isOver()) {
      try {
        Thread.sleep(4000);
        this.updateAllAuctions(true);
      } catch (InterruptedException e) {
        Logging.log("[+] woken: " + e.getMessage());
      }
    }
    this.updateAllAuctions(true);
    for (Account account : this.acctManager.getAccounts()) {
      System.out.println(account);
    }
  }
}






