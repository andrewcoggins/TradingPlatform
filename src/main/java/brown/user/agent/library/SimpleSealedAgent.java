package brown.user.agent.library;

import java.util.HashMap;
import java.util.Map;

import brown.logging.library.Logging;
import brown.mechanism.bid.library.BidType;
import brown.mechanism.bidbundle.library.OneSidedBidBundle;
import brown.mechanism.channel.library.OneSidedChannel;
import brown.mechanism.messages.library.AccountResetMessage;
import brown.mechanism.messages.library.BankUpdateMessage;
import brown.mechanism.messages.library.GameReportMessage;
import brown.mechanism.tradeable.ITradeable;
import brown.system.setup.library.SimpleSetup;

/**
 * Simple sealed agent bids in a simple sealed auction, 
 * just bids valuation. 
 * @author andrew
 *
 */
public class SimpleSealedAgent extends AbsAuctionAgent {
  
  public SimpleSealedAgent(String host, int port)
       {
    super(host, port, new SimpleSetup());
  }
  
  @Override
  public void onSimpleSealed(OneSidedChannel simpleChannel) {
    Map<ITradeable, BidType> initial = new HashMap<ITradeable, BidType>();
    for (ITradeable t: this.tradeables) {
      initial.put(t, new BidType(this.valuation.getValuation(t).doubleValue(), 1));
    }
    // just bid valuation 
    simpleChannel.bid(this, new OneSidedBidBundle(initial));
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