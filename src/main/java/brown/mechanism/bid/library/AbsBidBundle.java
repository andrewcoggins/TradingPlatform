package brown.mechanism.bid.library;

import java.util.Map;

import brown.mechanism.bid.IBid;
import brown.mechanism.tradeable.ITradeable;

/**
 * A map from Tradeables to a Bids.
 * @author andrew, modified by kerry
 *
 */
public abstract class AbsBidBundle implements IBid {
   
  public final Map<ITradeable, Double> bids;
  public final BidDirection direction; 
  
  public AbsBidBundle(Map<ITradeable, Double> bids) {
    this.bids = bids; 
    this.direction = null; 
  }
  
  public AbsBidBundle(Map<ITradeable, Double> bids, BidDirection direction) {
    this.bids = bids; 
    this.direction = direction; 
  }

  @Override
  public String toString() {
    return "AbsBidBundle [bids=" + bids + ", direction=" + direction + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((bids == null) ? 0 : bids.hashCode()); 
    result = prime * result + ((direction == null) ? 0 : direction.hashCode());
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
    AbsBidBundle other = (AbsBidBundle) obj;
    if (bids == null) {
      if (other.bids != null)
        return false;
    } else if (!bids.equals(other.bids))
      return false;
    if (direction != other.direction)
      return false;
    return true;
  }

  

  
}