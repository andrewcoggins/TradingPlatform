package brown.communication.bid.library;

import java.util.Map;

import brown.communication.bid.IBid;
import brown.platform.tradeable.ITradeable;

/**
 * A map from Tradeables to a Bids.
 * @author andrew, modified by kerry
 *
 */
public abstract class AbsBidBundle implements IBid {
   
  public final Map<ITradeable, Double> bids;
  
  public AbsBidBundle(Map<ITradeable, Double> bids) {
    this.bids = bids; 
  }
  
  @Override
  public String toString() {
    return "AbsBidBundle [bids=" + bids + "]";
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
    AbsBidBundle other = (AbsBidBundle) obj;
    if (bids == null) {
      if (other.bids != null)
        return false;
    } else if (!bids.equals(other.bids))
      return false;
    return true;
  }
   
}
