package brown.agent.library;


import brown.agent.AbsCallMarketAgent;
import brown.bid.library.BidDirection;
import brown.bid.library.TwoSidedBid;
import brown.bidbundle.library.TwoSidedBidBundle;
import brown.channels.library.CallMarketChannel;
import brown.exceptions.AgentCreationException;
import brown.logging.Logging;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.GameReportMessage;
import brown.setup.library.SSSPSetup;

public class TestCallMarketAgent extends AbsCallMarketAgent {
   private BidDirection direction;
   private Double price;
   
  
  public TestCallMarketAgent(String host, int port, BidDirection direction, Double price)
      throws AgentCreationException {
    super(host, port, new SSSPSetup());
    this.direction = direction;
    this.price = price;
  }
  
  @Override
  public void onCallMarket(CallMarketChannel cmChannel) {
    cmChannel.bid(this, new TwoSidedBidBundle(new TwoSidedBid(this.direction, this.price, 1)));
  }  

  @Override
  public void onBankUpdate(BankUpdateMessage bankUpdate) {
    Logging.log("BANKUPDATE: Agent: " + this.ID + ", " + bankUpdate.toString());
  }

  @Override
  public void onGameReport(GameReportMessage gameReport) {
    Logging.log("Game report received");
    Logging.log(gameReport.toString());
  } 
  
  public static void main(String[] args) throws AgentCreationException {
    while(true){}
  }  
}