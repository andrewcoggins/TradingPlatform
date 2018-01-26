package brown.agent.library;

import java.util.HashMap;
import java.util.Map;

import brown.agent.AbsSSSPAgent;
import brown.bidbundle.library.AuctionBidBundle;
import brown.channels.agent.library.AuctionChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.GameReportMessage;
import brown.setup.Logging;
import brown.setup.library.SSSPSetup;
import brown.tradeable.ITradeable;

public class SSSPAgent extends AbsSSSPAgent {
  
  public SSSPAgent(String host, int port)
      throws AgentCreationException {
    super(host, port, new SSSPSetup());
  }
  
  @Override
  public void onSSSP(AuctionChannel simpleChannel) {
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
    Logging.log(bankUpdate.toString());
  }

  @Override
  public void onGameReport(GameReportMessage gameReport) {
    Logging.log("Game report received");
  } 
  
  public static void main(String[] args) throws AgentCreationException {
    new SSSPAgent("localhost", 2121);
    new SSSPAgent("localhost", 2121);
    new SSSPAgent("localhost", 2121);    
    while(true){}
  }
  
}