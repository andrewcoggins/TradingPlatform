package brown.accounting.bid;

import java.util.Map;
import java.util.Set;

import brown.accounting.MarketState;
import brown.tradeable.library.Good;

/**
 * The lowest-level bidding datatype. A mapping from sets of tradeables to 
 * marketstates, which contains bid prices.
 * @author andrew
 */
public class ComplexBid extends AbsBid {
  
  public final Map<Set<Good>, MarketState> bids; 
  
  public ComplexBid(Map<Set<Good> , MarketState> bundle) { 
    this.bids = bundle; 
  }

  @Override
  public String toString() {
    return "ComplexBid [bids=" + bids + "]";
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
    ComplexBid other = (ComplexBid) obj;
    if (bids == null) {
      if (other.bids != null)
        return false;
    } else if (!bids.equals(other.bids))
      return false;
    return true;
  }
  
  
}