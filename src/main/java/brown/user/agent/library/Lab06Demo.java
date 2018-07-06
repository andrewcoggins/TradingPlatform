package brown.user.agent.library;

import brown.logging.library.Logging;
import brown.mechanism.bid.library.BidDirection;
import brown.mechanism.channel.library.CallMarketChannel;

/**
 * Demo agent for lab06 for cs1951k.
 * @author kerry
 *
 */
public class Lab06Demo extends AbsLab06Agent{
  private BidDirection direction;
  private double price;
  private double increment;
  private int quantity;
  
  public Lab06Demo(String host, int port, String name, BidDirection direction, double price, double increment, int quantity)
       {
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
      this.buy(price, quantity, channel);
      this.price = this.price+this.increment;
    } else {
      Logging.log(this.ID + " offering at " + this.price);
      this.sell(price, quantity, channel);
      this.price = this.price-this.increment;      
    }
    Logging.log("ORDERBOOK SIZE: " + getOrderBook().getBuys().size());
    Logging.log("Orderbook: " + getOrderBook().toString());
    Logging.log("LEDGER SIZE: " + getLedger().size());
    Logging.log("Ledger: " + getLedger().toString());
  }

  @Override
  public void onTransaction(int quantity, double price) {
  }
  
  public static void main(String[] args)  {
    new Lab06Demo("localhost", 2121,"buyer",BidDirection.BUY,50.,0,1);    
    new Lab06Demo("localhost", 2121,"seller",BidDirection.SELL,50.,0,1);            
    new Lab06Demo("localhost", 2121,"buyer",BidDirection.BUY,49.,0,1);    
    new Lab06Demo("localhost", 2121,"seller",BidDirection.SELL,51.,0,1);         
    while(true){}      
  }  
}
