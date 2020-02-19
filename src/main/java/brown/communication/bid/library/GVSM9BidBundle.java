package brown.communication.bid.library;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.util.Arrays;

import brown.communication.bid.IBidBundle;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;

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
  
	public GVSM9BidBundle(Map<IItem, Double> bids, boolean isNational) {
		super(mapOf(bids)); 
		this.isNational = isNational;
	}
	
	private static Map<ICart, Double> mapOf(Map<IItem, Double> bids) {
		Map<ICart, Double> m = new HashMap<>();
		for (Map.Entry<IItem, Double> ent : bids.entrySet()) {
			ICart cart = new Cart();
			cart.addToCart(ent.getKey());
			m.put(cart, ent.getValue());
		}
		return m;
	}
	
	public boolean isNational() {
		return this.isNational;
	}
	
}