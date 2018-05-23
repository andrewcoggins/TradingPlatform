package brown.agent.library;

import brown.agent.AbsCallMarketAgent;
import brown.bid.library.BidDirection;
import brown.bid.library.TwoSidedBid;
import brown.bidbundle.library.TwoSidedBidBundle;
import brown.channels.library.CallMarketChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.CallMarketReportMessage;
import brown.messages.library.GameReportMessage;
import brown.setup.library.CallMarketSetup;

/**
 * Bot for call market.
 * @author kerry
 *
 */
public class CallMarketLagAgent extends AbsCallMarketAgent {
  
  private int lagTime; 
  
  public CallMarketLagAgent(String host, int port, String name, int lagTime)
      throws AgentCreationException {
    super(host, port, new CallMarketSetup());
    this.lagTime = lagTime;
  }

  @Override
  public void onCallMarket(CallMarketChannel channel) {
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
  
  public static void main(String[] args) throws AgentCreationException { 
    new CallMarketLagAgent("localhost", 2121, "andrew", 3000);
    new CallMarketLagAgent("localhost", 2121, "andrew", 3000);
    while(true){}
  }

  
}