package brown.server.library; 

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.esotericsoftware.kryonet.Connection;

import brown.accounting.Account;
import brown.market.library.Market;
import brown.market.marketstate.library.InternalState;
import brown.market.preset.library.SimSecondPriceRules;
import brown.messages.library.Registration;
import brown.messages.library.ValuationRegistration;
import brown.server.AbsServer;
import brown.setup.Logging;
import brown.setup.ISetup;
import brown.setup.library.SimpleSetup;
import brown.tradeable.library.Tradeable;
import brown.value.valuation.library.AdditiveValuation;
import brown.value.valuationrepresentation.library.SimpleValuation;

public class SimultaneousSecondPriceServer extends AbsServer {

  
  private AdditiveValuation masterValuation; 
  private Integer numGoods = 5; 
  private Set<Tradeable> allGoods;
  private Integer numPlayers;
  
  public SimultaneousSecondPriceServer(int port, ISetup gameSetup) {
    super(port, gameSetup);
    //create set of goods and valuation
    this.allGoods = new HashSet<Tradeable>(); 
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
      //TODO: Rules for this game.
      this.manager.open(new Market(new SimSecondPriceRules(), new InternalState(0, new HashSet<Tradeable>())));
      delay(3, true);
    }
  }
  
  public static void main(String[] args) throws InterruptedException {
    SimultaneousSecondPriceServer sspServer = new SimultaneousSecondPriceServer(2121, new SimpleSetup());
    sspServer.runGame();
  }
   
}