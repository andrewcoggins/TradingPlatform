
package brown.communication.bid.library;

import java.util.Map;

import brown.communication.bid.IBidBundle;
import brown.platform.item.ICart;

/**
 * A map from Carts to a Bids.
 *
 */
public abstract class AbsOneSidedBidBundle implements IBidBundle {
   
  private Map<ICart, Double> bids; 
  
  public AbsOneSidedBidBundle(Map<ICart, Double> bids) {
    this.bids = bids; 
  }
  
  public Map<ICart, Double> getBids() {
    return this.bids; 
  }

  @Override
  public String toString() {
    return "AbsOneSidedBidBundle [bids=" + bids + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((bids == null) ? 0 : bids.hashCode());
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
    AbsOneSidedBidBundle other = (AbsOneSidedBidBundle) obj;
    if (bids == null) {
      if (other.bids != null)
        return false;
    } else if (!bids.equals(other.bids))
      return false;
    return true;
  }
   
}
