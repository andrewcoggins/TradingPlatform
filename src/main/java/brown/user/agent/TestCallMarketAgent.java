package brown.user.agent;


import brown.logging.Logging;
import brown.mechanism.bid.BidDirection;
import brown.mechanism.bid.TwoSidedBid;
import brown.mechanism.bidbundle.TwoSidedBidBundle;
import brown.mechanism.channel.CallMarketChannel;
import brown.platform.messages.BankUpdateMessage;
import brown.platform.messages.CallMarketReportMessage;
import brown.platform.messages.GameReportMessage;
import brown.system.setup.CallMarketSetup;

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
  public void onCallMarket(CallMarketChannel cmChannel) {
    
    
    for (int i = 0; i <20; i++){
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
