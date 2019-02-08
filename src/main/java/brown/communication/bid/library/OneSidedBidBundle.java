package brown.communication.bid.library;

import java.util.Map;

import brown.communication.bid.IBid;
import brown.platform.tradeable.ITradeable;

/**
 * A map from Tradeables to a Bids.
 * @author andrew, modified by kerry
 *
 */
public class OneSidedBidBundle extends AbsBidBundle implements IBid {
	
	public OneSidedBidBundle(Map<ITradeable, Double> bids) {
		super(bids);  
	}
	
}