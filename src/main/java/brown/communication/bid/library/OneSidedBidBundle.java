package brown.communication.bid.library;

import java.util.Map;

import brown.communication.bid.IBidBundle;
import brown.platform.item.ICart;

/**
 * A map from Tradeables to a Bids.
 * @author andrew
 *
 */
public class OneSidedBidBundle extends AbsOneSidedBidBundle implements IBidBundle {
	
  public OneSidedBidBundle() {
    super(); 
  }
  
	public OneSidedBidBundle(Map<ICart, Double> bids) {
		super(bids);  
	}
	
}