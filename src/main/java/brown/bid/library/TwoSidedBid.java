package brown.bid.library;

import brown.bid.IBid;
import brown.bidbundle.IBidBundle;

/**
 * A map from Tradeables to a Bids.
 * @author andrew, modified by kerry
 *
 */
public class TwoSidedBid implements IBid {
  
  public BidDirection direction;
  public Integer quantity;  
  public Double price; 
  
  /**
   * For Kryo 
   * DO NOT USE
   */
  public TwoSidedBid() {
    this.direction = null;
    this.quantity = null;
    this.price = null;
  }
  
  /**
   * @param bids are represented as a map from ITradeables to doubles
   */
  public TwoSidedBid(BidDirection direction, double price, int quantity) {
    this.direction = direction;
    this.price = price;
    this.quantity = quantity;
  }
  
  @Override
  public String toString() {
    return "Two Sided Bid: Direction: " + direction + ", Price: " + price + ", Quantity: " + quantity ;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((direction == null) ? 0 : direction.hashCode());
    long temp;
    temp = Double.doubleToLongBits(price);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + quantity;
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
    TwoSidedBid other = (TwoSidedBid) obj;
    if (direction != other.direction)
      return false;
    if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
      return false;
    if (quantity != other.quantity)
      return false;
    return true;
  } 
}