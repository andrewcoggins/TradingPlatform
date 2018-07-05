package brown.platform.twosided;

import java.util.Map;

import brown.logging.Logging;
import brown.mechanism.bid.BidDirection;
import brown.mechanism.bid.TwoSidedBid;

/**
 * a BuyOrder is a request to buy in a two-sided market.
 * @author acoggins
 *
 */
public class BuyOrder implements Comparable<BuyOrder> {
  public final Integer agent;
  public final Integer quantity;
  public final Double price;
  
  /**
   * For Kryo do not use
   */
  public BuyOrder() {
    this.agent = null;
    this.quantity = null;
    this.price = null;
  }
  
  /**
   * a BuyOrder can be represented as a quantity price, and 
   * the private ID of an agent.
   * @param agent
   * @param quantity
   * @param price
   */
  public BuyOrder(Integer agent, Integer quantity, Double price) {
    this.agent = agent;
    this.quantity = quantity;
    this.price = price;
  }
  
  /**
   * a BuyOrder can be represented as a TwoSidedBid and the 
   * private ID of an agent.
   * @param bid
   * @param agent
   */
  public BuyOrder(TwoSidedBid bid, Integer agent) {
    if (bid.direction != BidDirection.BUY){
      Logging.log("Attempting to create sell order with wrong bid direction");
    }
    this.agent = agent;
    this.quantity = bid.quantity;
    this.price = bid.price;
  }
  
 public BuyOrder sanitize(Integer agent, Map<Integer, Integer> privateToPublic){ 
   Integer id = this.agent;
   if (!this.agent.equals(agent)){
     id = 0;
   }
   return new BuyOrder(id, this.quantity, this.price);
 }

@Override
public String toString() {
  return "BuyOrder [agent=" + agent + ", quantity=" + quantity + ", price="
      + price + "]";
}

@Override
public int hashCode() {
  final int prime = 31;
  int result = 1;
  result = prime * result + ((agent == null) ? 0 : agent.hashCode());
  result = prime * result + ((price == null) ? 0 : price.hashCode());
  result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
  return result;
}

@Override
public boolean equals(Object obj) {
  if (this == obj)
    return true;
  if (obj == null)
    return false;
  if (getClass() != obj.getClass())
    return false;
  BuyOrder other = (BuyOrder) obj;
  if (agent == null) {
    if (other.agent != null)
      return false;
  } else if (!agent.equals(other.agent))
    return false;
  if (price == null) {
    if (other.price != null)
      return false;
  } else if (!price.equals(other.price))
    return false;
  if (quantity == null) {
    if (other.quantity != null)
      return false;
  } else if (!quantity.equals(other.quantity))
    return false;
  return true;
}

@Override
public int compareTo(BuyOrder o) {
  return -1*Double.compare(this.price,o.price);
}

}


