package brown.accounting.bid;

import java.util.Map;

import brown.accounting.MarketState;
import brown.tradeable.library.Tradeable;

/**
 * The lowest-level bidding datatype. A simple mapping from a tradeable to 
 * a marketstate, which contains a bid price.
 * 
 * @author andrew
 *
 */
public class SimpleBid extends AbsBid {
  
  public final Map<Tradeable, MarketState> bids;
  
  public SimpleBid(Map<Tradeable, MarketState> bids) {
    this.bids = bids; 
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
    SimpleBid other = (SimpleBid) obj;
    if (bids == null) {
      if (other.bids != null)
        return false;
    } else if (!bids.equals(other.bids))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "SimpleBid [bids=" + bids + "]";
  }
   
}