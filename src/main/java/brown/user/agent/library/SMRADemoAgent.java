package brown.user.agent.library; 

import java.util.HashMap;
import java.util.Map;

import brown.mechanism.bid.library.BidType;
import brown.mechanism.bidbundle.IBidBundle;
import brown.mechanism.bidbundle.library.AuctionBidBundle;
import brown.mechanism.channel.library.OpenOutcryChannel;
import brown.mechanism.channel.library.SealedBidChannel;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.messages.library.GameReportMessage;
import brown.system.setup.library.SMRASetup;

/**
 * Demo agent for SMRA auction- never went into use for cs1951k
 * @author acoggins
 *
 */
public class SMRADemoAgent extends AbsSMRAAgent {

  public SMRADemoAgent(String host, int port)
       {
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
  public void onSimpleSealed(SealedBidChannel channel) {
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
  
  public static void main(String[] args)  {
    new SMRADemoAgent("localhost", 2121); 
    while(true){}
  }
}