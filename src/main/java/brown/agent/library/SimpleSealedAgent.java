package brown.agent.library;

import java.util.HashMap;
import java.util.Map;

import brown.agent.AbsAuctionAgent;
import brown.bid.interim.BidType;
import brown.bidbundle.library.AuctionBidBundle;
import brown.channels.library.AuctionChannel;
import brown.exceptions.AgentCreationException;
import brown.logging.Logging;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.GameReportMessage;
import brown.setup.library.SSSPSetup;
import brown.tradeable.ITradeable;

/**
 * Simple sealed agent bids in a simple sealed auction, 
 * just bids valuation. 
 * @author andrew
 *
 */
public class SimpleSealedAgent extends AbsAuctionAgent {
  
  public SimpleSealedAgent(String host, int port)
      throws AgentCreationException {
    super(host, port, new SSSPSetup());
  }
  
  @Override
  public void onSimpleSealed(AuctionChannel simpleChannel) {
    Map<ITradeable, BidType> initial = new HashMap<ITradeable, BidType>();
    for (ITradeable t: this.tradeables) {
      initial.put(t, new BidType(this.valuation.getValuation(t).doubleValue(), 1));
    }
    // just bid valuation 
    simpleChannel.bid(this, new AuctionBidBundle(initial));
  }  

  @Override
  public void onBankUpdate(BankUpdateMessage bankUpdate) {
    Logging.log("BANKUPDATE: Agent: " + this.ID + ", " + bankUpdate.toString());
  }

  @Override
  public void onGameReport(GameReportMessage gameReport) {
    Logging.log("Game report received");
  } 
  
  public static void main(String[] args) throws AgentCreationException {
    new SimpleSealedAgent("localhost", 2121);
    new SimpleSealedAgent("localhost", 2121);
//    new SimpleSealedAgent("localhost", 2121);
//    new SimpleSealedAgent("localhost", 2121);
//    new SimpleSealedAgent("localhost", 2121);
//    new SimpleSealedAgent("localhost", 2121);
//    new SimpleSealedAgent("localhost", 2121);    
//    new SimpleSealedAgent("localhost", 2121);
//    new SimpleSealedAgent("localhost", 2121);    
    while(true){}
  }  
}