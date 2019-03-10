package brown.communication.bid.library;

import java.util.Map;

import brown.communication.bid.IBid;
import brown.platform.tradeable.ITradeable;

/**
 * A map from Tradeables to a Bids.
 * @author andrew, modified by kerry
 *
 */
public class TwoSidedBidBundle extends AbsTwoSidedBidBundle implements IBid {

  public TwoSidedBidBundle(Map<ITradeable, Double> bids, BidDirection direction) {
  	super(bids, direction); 
  }

}
