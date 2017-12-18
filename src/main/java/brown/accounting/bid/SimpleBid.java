package brown.accounting.bid;

import java.util.Map;

import brown.accounting.MarketState;
import brown.tradeable.library.Tradeable;

public class SimpleBid extends AbsBid {
  
  public final Map<Tradeable, MarketState> bids;
  
  public SimpleBid(Map<Tradeable, MarketState> bids) {
    this.bids = bids; 
  }

  @Override
  public String toString() {
    return "SimpleBid [bids=" + bids + "]";
  }
  
  
}