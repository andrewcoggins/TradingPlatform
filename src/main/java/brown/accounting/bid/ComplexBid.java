package brown.accounting.bid;

import java.util.Map;
import java.util.Set;

import brown.accounting.MarketState;
import brown.tradeable.library.Tradeable;

/**
 * The lowest-level bidding datatype. A mapping from sets of tradeables to 
 * marketstates, which contains bid prices.
 * @author andrew
 */
public class ComplexBid extends AbsBid {
  
  public final Map<Set<Tradeable>, MarketState> bids; 
  
  public ComplexBid(Map<Set<Tradeable> , MarketState> bundle) { 
    this.bids = bundle; 
  }

  @Override
  public String toString() {
    return "ComplexBid [bids=" + bids + "]";
  }
  
}