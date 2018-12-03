package brown.user.agent.library;


import brown.logging.library.Logging;
import brown.mechanism.bid.library.BidDirection;
import brown.mechanism.bid.library.TwoSidedBid;
import brown.mechanism.bidbundle.library.TwoSidedBidBundle;
import brown.mechanism.channel.library.TwoSidedChannel;
import brown.platform.messages.library.BankUpdateMessage;
import brown.platform.messages.library.CallMarketReportMessage;
import brown.platform.messages.library.GameReportMessage;
import brown.system.setup.library.CallMarketSetup;

/**
 * Test agent for call market agent. 
 * @author kerry
 *
 */
public class TestCallMarketAgent extends AbsCallMarketAgent {
   private BidDirection direction;
   private Double price;
   private Integer quantity;
   
  
  public TestCallMarketAgent(String host, int port, String name, BidDirection direction, Double price, Integer quantity) {
    super(host, port, new CallMarketSetup(), name);
    this.direction = direction;
    this.price = price;
    this.quantity = quantity;
  }
  
  @Override
  public void onCallMarket(TwoSidedChannel cmChannel) {
    
    
    for (int i = 0; i <20; i++) {
      cmChannel.bid(this, new TwoSidedBidBundle(new TwoSidedBid(this.direction, this.price, this.quantity)));      
    }
    Logging.log("Orderbook SIZE: " + (cmChannel.getOrderBook().getBuys().size() + cmChannel.getOrderBook().getSells().size()));
    if (this.direction == BidDirection.SELL){
      Logging.log("AGENT " + this.ID + "selling at " + this.price);      
    } else {
      Logging.log("AGENT " + this.ID + " bidding at " + this.price);            
    }
  }  

  @Override
  public void onBankUpdate(BankUpdateMessage bankUpdate) {
    Logging.log("BANKUPDATE: Agent: " + this.ID + ", " + bankUpdate.toString());
  }

  @Override
  public void onGameReport(GameReportMessage gameReport) {
    Logging.log("Game report received");
    if (gameReport instanceof CallMarketReportMessage) {
      Logging.log("size: " + ((CallMarketReportMessage) gameReport).getTransactions().size());
    } else {
      Logging.log(gameReport.toString());      
    }
  } 
  
  public static void main(String[] args) {
    new UpdateAgent("localhost", 2121,"update");    
    new FixedAgent("localhost", 2121,"f1");    
    new FixedAgent("localhost", 2121,"f2");    
    new FixedAgent("localhost", 2121,"f3");        
      while(true){}      
  }  
}
