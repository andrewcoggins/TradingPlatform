package brown.bid.library;

import brown.bid.IBid;

public class CancelBid implements IBid{
  
  public BidDirection direction;
  public Double price; 
  
  /**
   * For Kryo 
   * DO NOT USE
   */
  public CancelBid() {
    this.direction = null;
    this.price = null;
  }
  
  /**
   * @param bids are represented as a map from ITradeables to doubles
   */
  public CancelBid(BidDirection direction, double price) {
    this.direction = direction;
    this.price = price;    
  }

  @Override
  public String toString() {
    return "CancelBid [direction=" + direction + ", price=" + price + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((direction == null) ? 0 : direction.hashCode());
    result = prime * result + ((price == null) ? 0 : price.hashCode());
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
    CancelBid other = (CancelBid) obj;
    if (direction != other.direction)
      return false;
    if (price == null) {
      if (other.price != null)
        return false;
    } else if (!price.equals(other.price))
      return false;
    return true;
  }
  
}
