package brown.server.library; 

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.esotericsoftware.kryonet.Connection;

import brown.assets.accounting.Account;
import brown.marketinternalstates.InternalState;
import brown.markets.Market;
import brown.markets.presets.SimSecondPriceRules;
import brown.messages.Registration;
import brown.registration.ValuationRegistration;
import brown.server.TradingServer;
import brown.setup.Logging;
import brown.setup.Setup;
import brown.setup.library.SimpleSetup;
import brown.tradeables.Asset;
import brown.valuable.library.SimpleValuation;
import brown.valuable.library.Tradeable;
import brown.valuation.library.AdditiveValuation;

public class SimultaneousSecondPriceServer extends TradingServer {

  
  private AdditiveValuation masterValuation; 
  private Integer numGoods; 
  private Set<Tradeable> allGoods;
  private Integer numPlayers;
  
  public SimultaneousSecondPriceServer(int port, Setup gameSetup) {
    super(port, gameSetup);
    //create set of goods and valuation
    allGoods = new HashSet<Tradeable>(); 
    for(int i = 0; i < numGoods; i++) {
      this.allGoods.add(new Tradeable(i));
    }
    this.numPlayers = 0;
    this.masterValuation = new AdditiveValuation(this.allGoods);
  } 

  
  @Override
  protected void onRegistration(Connection connection, Registration registration) {
    //give agent private ID
    Integer agentID = this.defaultRegistration(connection, registration);
    if (agentID == null) {
      return;
    }
    //give agent access to private valuation
    SimpleValuation privateValuation = masterValuation.getValuation(this.allGoods);
    this.numPlayers++;
    this.theServer.sendToTCP(connection.getID(), new ValuationRegistration(agentID, privateValuation, masterValuation));
    //give agent some money
    Account oldAccount = this.acctManager.getAccount(connections.get(connection));
    Account newAccount = oldAccount.addAll(10000, null); 
    this.acctManager.setAccount(connections.get(connection), newAccount);
    List<Integer> anID = new ArrayList<Integer>();
    anID.add(agentID);
    this.sendBankUpdates(anID);
    
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
  
  public void runGame() throws InterruptedException {
    int time = 0; 
    while (time < 10) {
      Thread.sleep(1000); 
      Logging.log("[-] setup phase " + time++);
    }
    for(int i = 0; i < 5; i++) { 
      //TODO: Rules for this kind of game.
      this.manager.open(new Market(new SimSecondPriceRules(), new InternalState(0, new HashSet<Asset>())));
      delay(3, true);
    }
  }
  
  public static void main(String[] args) throws InterruptedException {
    SimultaneousSecondPriceServer sspServer = new SimultaneousSecondPriceServer(2121, new SimpleSetup());
    sspServer.runGame();
  }
   
}