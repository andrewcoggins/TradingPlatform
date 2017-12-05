package brown.server.library; 

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import brown.market.library.Market;
import brown.market.marketstate.library.InternalState;
import brown.market.preset.AbsMarketPreset;
import brown.server.AbsServer;
import brown.setup.ISetup;
import brown.setup.Logging;
import brown.tradeable.library.Tradeable;
import brown.value.config.AbsValueConfig;

public class RunServer extends AbsServer {

  public RunServer(int port, ISetup gameSetup) {
    super(port, gameSetup);
  }
  
//  //may need to wash out some initial assumptions here.
//  @Override
//  protected void onRegistration(Connection connection,
//      Registration registration) {
//  //give agent private ID
//    Integer agentID = this.defaultRegistration(connection, registration);
//    if (agentID == null) {
//      return;
//    }
//    AbsValuationRepresentation privateValuation = aValuation.getValuation(this.allGoods);
//    this.theServer.sendToTCP(connection.getID(), new ValuationRegistration(agentID, privateValuation, aValuation));
//    //give agent some money
//    Account oldAccount = this.acctManager.getAccount(connections.get(connection));
//    Account newAccount = oldAccount.addAll(10000, null); 
//    this.acctManager.setAccount(connections.get(connection), newAccount);
//    List<Integer> anID = new ArrayList<Integer>();
//    anID.add(agentID);
//    this.sendBankUpdates(anID);
//  }
  
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
      AbsValueConfig valueInfo) throws InterruptedException {
    delay(10);
    this.valueConfig = new HashMap<Integer, AbsValueConfig>(); 
    for (AbsMarketPreset ruleSet : presets) {
      this.manager.open(new Market(presets.get(presets.indexOf(ruleSet)),
          new InternalState(0, new HashSet<Tradeable>())));
      this.valueConfig.put(presets.indexOf(ruleSet), valueInfo);
    }
    this.completeAuction(0);
  } 
  
}