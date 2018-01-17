package brown.accounting.bid;

import java.util.Map;

import brown.tradeable.library.Tradeable;

/**
 * A simple mapping from a tradeable to 
 * a double, which contains a bid price.
 * 
 * @author andrew, modified by kerry
 *
 */
public class SimpleBid extends AbsBid {
  
  public final Map<Tradeable, Double> bids;
  
  public SimpleBid(Map<Tradeable, Double> bids) {
    this.bids = bids; 
  }
  
  @Override
  public String toString() {
    return "SimpleBid [bids=" + bids + "]";
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
    return(obj instanceof SimpleBid && 
        ((SimpleBid) obj).bids.equals(this.bids));
  }
   
}