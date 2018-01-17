package brown.accounting.bid;

import java.util.Map;
import java.util.Set;

import brown.tradeable.library.Tradeable;

/**
 * The lowest-level bidding datatype. A mapping from sets of tradeables to 
 * doubles, which contains bid prices.
 * 
 * @author andrew, modified by kerry
 */
public class ComplexBid extends AbsBid {
  
  public final Map<Set<Tradeable>, Double> bids; 
  
  public ComplexBid(Map<Set<Tradeable> , Double> bundle) { 
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
    return(obj instanceof ComplexBid && 
        ((ComplexBid) obj).bids.equals(this.bids));
  }
  
}