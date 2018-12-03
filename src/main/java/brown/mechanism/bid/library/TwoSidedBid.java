package brown.mechanism.bid.library;

import java.util.Map;

import brown.mechanism.bid.IGameAction;
import brown.mechanism.tradeable.ITradeable;

/**
 * A map from Tradeables to a Bids.
 * @author andrew, modified by kerry
 *
 */
public class TwoSidedBid implements IGameAction {
  
public final Map<ITradeable, Double> bids; 
public final BidDirection direction; 

public TwoSidedBid(Map<ITradeable, Double> bids, BidDirection direction) {
	this.bids = bids; 
	this.direction = direction; 
}

}