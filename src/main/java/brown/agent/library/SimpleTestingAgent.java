package brown.agent.library;

import brown.agent.AbsLab06Agent;
import brown.bid.library.BidDirection;
import brown.channels.library.CallMarketChannel;
import brown.exceptions.AgentCreationException;
import brown.logging.Logging;

public class SimpleTestingAgent extends AbsLab06Agent{
  private BidDirection direction;
  private double price;
  private double increment;
  private int quantity;
  
  public SimpleTestingAgent(String host, int port, String name, BidDirection direction, double price, double increment, int quantity)
      throws AgentCreationException {
    super(host, port, name);
    this.direction = direction;
    this.price = price;
    this.increment = increment;
    this.quantity = quantity;
  }

  @Override
  public void onMarketStart() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onMarketRequest(CallMarketChannel channel) {
    if (this.direction == BidDirection.BUY){
      Logging.log(this.ID + " bidding for " + this.price);
      for (int i = 0; i <200; i++){
        this.buy(price, quantity, channel);
      }
      this.price = this.price+this.increment;
    } else {
      Logging.log(this.ID + " offering at " + this.price);
      for (int i = 0; i <200; i++){
        this.sell(price, quantity, channel);
      }
      this.price = this.price-this.increment;      
    }
    Logging.log("ORDERBOOK SIZE: " + getOrderBook().getBuys().size());
    Logging.log("LEDGER SIZE: " + getLedger().size());
  }

  @Override
  public void onTransaction(int quantity, double price) {
  }
  
  public static void main(String[] args) throws AgentCreationException {
    new SimpleTestingAgent("localhost", 2121,"buyer",BidDirection.BUY,50.,0,1);    
    new SimpleTestingAgent("localhost", 2121,"seller",BidDirection.SELL,50.,0,1);            
    new SimpleTestingAgent("localhost", 2121,"buyer",BidDirection.BUY,49.,0,1);    
    new SimpleTestingAgent("localhost", 2121,"seller",BidDirection.SELL,51.,0,1);         
      while(true){}      
  }  
}
