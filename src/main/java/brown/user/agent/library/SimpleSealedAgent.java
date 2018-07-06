package brown.user.agent.library;

import java.util.HashMap;
import java.util.Map;

import brown.logging.library.Logging;
import brown.mechanism.bid.library.BidType;
import brown.mechanism.bidbundle.library.AuctionBidBundle;
import brown.mechanism.channel.library.SealedBidChannel;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.messages.library.BankUpdateMessage;
import brown.platform.messages.library.GameReportMessage;
import brown.system.setup.library.SSSPSetup;

/**
 * Simple sealed agent bids in a simple sealed auction, 
 * just bids valuation. 
 * @author andrew
 *
 */
public class SimpleSealedAgent extends AbsAuctionAgent {
  
  public SimpleSealedAgent(String host, int port)
       {
    super(host, port, new SSSPSetup());
  }
  
  @Override
  public void onSimpleSealed(SealedBidChannel simpleChannel) {
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
  
  public static void main(String[] args)  {
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