package brown.mechanism.bid.library;

import java.util.Map;

import brown.mechanism.bid.IBid;
import brown.mechanism.tradeable.ITradeable;

/**
 * A map from Tradeables to a Bids.
 * @author andrew, modified by kerry
 *
 */
public abstract class AbsTwoSidedBidBundle extends AbsBidBundle implements IBid {
   
  public final BidDirection direction; 
  
  public AbsTwoSidedBidBundle(Map<ITradeable, Double> bids, BidDirection direction) {
    super(bids);
    this.direction = direction; 
  }

  @Override
  public String toString() {
    return "AbsTwoSidedBidBundle [direction=" + direction + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((direction == null) ? 0 : direction.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbsTwoSidedBidBundle other = (AbsTwoSidedBidBundle) obj;
    if (direction != other.direction)
      return false;
    return true;
  }

}