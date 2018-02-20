package brown.market.marketstate.library;

import java.util.Map;

import brown.bid.library.BidDirection;
import brown.bid.library.TwoSidedBid;
import brown.logging.Logging;

public class SellOrder {
  public final Integer agent;
  public final Integer quantity;
  public final Double price;
  
  /**
   * For Kryo do not use
   */
  public SellOrder() {
    this.agent = null;
    this.quantity = null;
    this.price = null;
  }
  
  public SellOrder(Integer agent, Integer quantity, Double price) {
    this.agent = agent;
    this.quantity = quantity;
    this.price = price;
  }
  
  public SellOrder(TwoSidedBid bid, Integer agent) {
    if (bid.direction != BidDirection.SELL){
      Logging.log("Attempting to create sell order with wrong bid direction");
    }
    this.agent = agent;
    this.quantity = bid.quantity;
    this.price = bid.price;
  }
  
 public SellOrder sanitize(Integer agent, Map<Integer, Integer> privateToPublic){ 
   Integer id = privateToPublic.get(this.agent);
   if (this.agent == agent){
     id = this.agent;
   }
   return new SellOrder(id, this.quantity, this.price);
 }
}
