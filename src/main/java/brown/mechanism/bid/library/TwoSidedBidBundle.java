package brown.mechanism.bid.library;

import java.util.Map;

import brown.mechanism.bid.IBid;
import brown.mechanism.tradeable.ITradeable;

/**
 * A map from Tradeables to a Bids.
 * @author andrew, modified by kerry
 *
 */
public class TwoSidedBidBundle extends AbsBidBundle implements IBid {
  
    
  public TwoSidedBidBundle(Map<ITradeable, Double> bids, BidDirection direction) {
  	super(bids, direction); 
  }

}