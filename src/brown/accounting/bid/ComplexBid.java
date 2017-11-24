package brown.accounting.bid;

import java.util.Map;
import java.util.Set;

import brown.accounting.MarketState;
import brown.tradeable.library.Tradeable;

public class ComplexBid extends AbsBid {
  
  
  public final Map<Set<Tradeable>, MarketState> bids; 
  
  public ComplexBid(Map<Set<Tradeable> , MarketState> bundle) { 
    this.bids = bundle; 
  }
  
 
}