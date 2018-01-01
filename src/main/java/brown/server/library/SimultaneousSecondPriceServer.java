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
import brown.messages.library.RegistrationMessage;
import brown.messages.library.ValuationRegistrationMessage;
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
  protected void onRegistration(Connection connection, RegistrationMessage registration) {
    //give agent private ID
    Integer agentID = this.defaultRegistration(connection, registration);
    if (agentID == null) {
      return;
    }
    //give agent access to private valuation
    SimpleValuation privateValuation = masterValuation.getValuation(this.allGoods);
    this.numPlayers++;
    this.theServer.sendToTCP(connection.getID(), new ValuationRegistrationMessage(agentID, privateValuation, masterValuation));
    //give agent some money
    Account acct = this.acctManager.getAccount(connections.get(connection));
    acct.add(10000); 
    //not sure if necessary
    this.acctManager.setAccount(connections.get(connection), acct);
    List<Integer> anID = new ArrayList<Integer>();
    anID.add(agentID);
    this.sendBankUpdates(anID);
    
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
    int time = 0; 
    while (time < 10) {
      Thread.sleep(1000); 
      Logging.log("[-] setup phase " + time++);
    }
    for(int i = 0; i < 5; i++) { 
      Set<Tradeable> someGoods = new HashSet<Tradeable>(); 
      for(int j = 0; j < numGoods; j++) {
        someGoods.add(new Tradeable(j));
      }
      this.manager.open(new Market(new SimSecondPriceRules(), new InternalState(0, someGoods)));
      this.completeAuction(0);
      for (Account acct : this.acctManager.getAccounts()) 
        Logging.log(acct.toString()); 
    }
  }
  
  public static void main(String[] args) throws InterruptedException {
    SimultaneousSecondPriceServer sspServer = new SimultaneousSecondPriceServer(2121, new SimpleSetup());
    sspServer.runGame();
  }
   
}