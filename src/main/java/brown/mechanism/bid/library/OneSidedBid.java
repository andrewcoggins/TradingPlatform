package brown.mechanism.bid.library;

import java.util.Map;

import brown.mechanism.bid.IGameAction;
import brown.mechanism.tradeable.ITradeable;

/**
 * A map from Tradeables to a Bids.
 * @author andrew, modified by kerry
 *
 */
public class OneSidedBid implements IGameAction {
   
	public final Map<ITradeable, Double> bids;
	
	public OneSidedBid(Map<ITradeable, Double> bids) {
		this.bids = bids; 
	}

	
}