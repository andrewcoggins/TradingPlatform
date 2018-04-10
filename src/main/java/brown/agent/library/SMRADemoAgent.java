package brown.agent.library; 

import java.util.HashMap;
import java.util.Map;

import brown.agent.AbsSMRAAgent;
import brown.bid.interim.BidType;
import brown.bidbundle.IBidBundle;
import brown.bidbundle.library.AuctionBidBundle;
import brown.channels.library.AuctionChannel;
import brown.channels.library.OpenOutcryChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.GameReportMessage;
import brown.setup.library.SMRASetup;
import brown.tradeable.ITradeable;

public class SMRADemoAgent extends AbsSMRAAgent {

  public SMRADemoAgent(String host, int port)
      throws AgentCreationException {
    super(host, port, new SMRASetup());
    // TODO Auto-generated constructor stub
  }

  @Override
  public void onOpenOutcry(OpenOutcryChannel channel) {
    // TODO decide how to bid in price discovery rounds.
    System.out.println("OPEN OUTCRY TRADE REQUEST RECEIVED"); 
    Map<ITradeable, BidType> bMap = new HashMap<ITradeable, BidType>();
    for (ITradeable t : this.tradeables) {
      bMap.put(t, new BidType(1.0, 1)); 
    }
    System.out.println("BID MAP: " + bMap);
    IBidBundle allGoods = new AuctionBidBundle(bMap); 
    channel.bid(this, allGoods);
  }

  @Override
  public void onSimpleSealed(AuctionChannel channel) {
    System.out.println("SIMPLE SEALED TRADE REQUEST RECEIVED"); 
 // something dumb
    Map<ITradeable, BidType> bMap = new HashMap<ITradeable, BidType>();
    for (ITradeable t : this.tradeables) {
      bMap.put(t, new BidType(1.0, 1)); 
    }
    IBidBundle allGoods = new AuctionBidBundle(bMap); 
    channel.bid(this, allGoods);
  }

  @Override
  public void onGameReport(GameReportMessage gameReport) {
    // TODO decide what to do with information.
    System.out.println("GAMEREPORT RECEIVED");
  }
  
  public static void main(String[] args) throws AgentCreationException {
    new SMRADemoAgent("localhost", 2121); 
    while(true){}
  }
}