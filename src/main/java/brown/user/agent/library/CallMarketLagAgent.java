package brown.user.agent.library;

import brown.mechanism.bid.library.BidDirection;
import brown.mechanism.bid.library.TwoSidedBid;
import brown.mechanism.bidbundle.library.TwoSidedBidBundle;
import brown.mechanism.channel.library.TwoSidedChannel;
import brown.platform.messages.library.CallMarketReportMessage;
import brown.platform.messages.library.GameReportMessage;
import brown.system.setup.library.CallMarketSetup;

/**
 * Bot for call market.
 * @author kerry
 *
 */
public class CallMarketLagAgent extends AbsCallMarketAgent {
  
  private int lagTime; 
  
  public CallMarketLagAgent(String host, int port, String name, int lagTime) {
    super(host, port, new CallMarketSetup());
    this.lagTime = lagTime;
  }

  @Override
  public void onCallMarket(TwoSidedChannel channel) {
    try {
      Thread.sleep(this.lagTime);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(channel.getOrderBook());
    channel.bid(this, new TwoSidedBidBundle(new TwoSidedBid(BidDirection.BUY, 100, 1)));
  }

  @Override
  public void onGameReport(GameReportMessage gameReport) {
    if (gameReport instanceof CallMarketReportMessage) {
      System.out.println(((CallMarketReportMessage) gameReport).getTransactions());
    }
  }
  
  public static void main(String[] args) { 
    new CallMarketLagAgent("localhost", 2121, "andrew", 3000);
    new CallMarketLagAgent("localhost", 2121, "andrew", 3000);
    while(true){}
  }

  
}