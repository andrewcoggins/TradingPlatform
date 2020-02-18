package brown.communication.bid.library;

import java.util.HashMap;
import java.util.Map;

import brown.communication.bid.IBidBundle;
import brown.platform.item.ICart;

/**
 * A map from Tradeables to a Bids.
 * @author andrew
 *
 */
public class GVSM9BidBundle extends AbsOneSidedBidBundle implements IBidBundle {
	private boolean isNational;
	
  public GVSM9BidBundle() {
    super(); 
    this.isNational = false;
  }
  
	public GVSM9BidBundle(ICart items, double bid, boolean isNational) {
		super(mapOf(items, bid)); 
		this.isNational = isNational;
	}
	
	private static Map<ICart, Double> mapOf(ICart items, double bid) {
		Map<ICart, Double> m = new HashMap<>();
		m.put(items, bid);
		return m;
	}
	
	public boolean isNational() {
		return this.isNational;
	}
	
}