package brown.agent.library;

import java.util.HashMap;
import java.util.Map;

import brown.agent.AbsSSSPAgent;
import brown.bid.bidbundle.library.AuctionBidBundle;
import brown.channels.agent.library.SSSPChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.RegistrationMessage;
import brown.setup.Logging;
import brown.setup.library.SSSPSetup;
import brown.tradeable.ITradeable;

public class SSSPAgent extends AbsSSSPAgent {
  
  public SSSPAgent(String host, int port)
      throws AgentCreationException {
    super(host, port, new SSSPSetup());
  }

  @Override
  public void onRegistration(RegistrationMessage registration) {
    this.ID = registration.getID();
    Logging.log("[*] Registered with server.");
  }
  
  @Override
  public void onSSSP(SSSPChannel simpleChannel) {
    Map<ITradeable, Double> initial = new HashMap<ITradeable, Double>();
    System.out.println(this.tradeables);
    for (ITradeable t: this.tradeables) {
      initial.put(t, this.valuation.getValuation(t).doubleValue());
    }
    // just bid valuation 
    simpleChannel.bid(this, new AuctionBidBundle(initial));
  }  

  @Override
  public void onBankUpdate(BankUpdateMessage bankUpdate) {
    System.out.println("bank update");
    System.out.println(bankUpdate.toString());
  }

  // to do
  @Override
  public void onGameReport(GameReportMessage gameReport) {
    System.out.println("market update");
  } 
  
  public static void main(String[] args) throws AgentCreationException {
    new SSSPAgent("localhost", 2121);
    new SSSPAgent("localhost", 2121);
    new SSSPAgent("localhost", 2121);    
    while(true){}
  }
}