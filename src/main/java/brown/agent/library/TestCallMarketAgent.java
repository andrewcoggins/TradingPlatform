package brown.agent.library;


import brown.agent.AbsCallMarketAgent;
import brown.bid.library.BidDirection;
import brown.bid.library.TwoSidedBid;
import brown.bidbundle.library.TwoSidedBidBundle;
import brown.channels.library.CallMarketChannel;
import brown.exceptions.AgentCreationException;
import brown.logging.Logging;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.CallMarketReportMessage;
import brown.messages.library.GameReportMessage;
import brown.setup.library.CallMarketSetup;

public class TestCallMarketAgent extends AbsCallMarketAgent {
   private BidDirection direction;
   private Double price;
   private Integer quantity;
   
  
  public TestCallMarketAgent(String host, int port, BidDirection direction, Double price, Integer quantity)
      throws AgentCreationException {
    super(host, port, new CallMarketSetup());
    this.direction = direction;
    this.price = price;
    this.quantity = quantity;
  }
  
  @Override
  public void onCallMarket(CallMarketChannel cmChannel) {
    Logging.log("AGENT " + this.ID + "bidding at " + this.price);
    cmChannel.bid(this, new TwoSidedBidBundle(new TwoSidedBid(this.direction, this.price, this.quantity)));
    if (this.direction == BidDirection.SELL){
      this.price = price+1; 
    } else {
      this.price = price-1;       
    }
  }  

  @Override
  public void onBankUpdate(BankUpdateMessage bankUpdate) {
    Logging.log("BANKUPDATE: Agent: " + this.ID + ", " + bankUpdate.toString());
  }

  @Override
  public void onGameReport(GameReportMessage gameReport) {
    Logging.log("Game report received");
    if (gameReport instanceof CallMarketReportMessage){
      Logging.log("size: " + ((CallMarketReportMessage) gameReport).getTransactions().size());
    } else {
      Logging.log(gameReport.toString());      
    }
  } 
  
  public static void main(String[] args) throws AgentCreationException {
    new TestCallMarketAgent("localhost", 2121,BidDirection.BUY,30.,1);    
      new TestCallMarketAgent("localhost", 2121,BidDirection.BUY,30.,1);
      new TestCallMarketAgent("localhost", 2121,BidDirection.SELL,20.,2);
      while(true){}      
  }  
}