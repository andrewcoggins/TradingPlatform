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
	private int agentNum;
	
  public GVSM9BidBundle() {
    super(); 
    this.agentNum = 0;
  }
  
	public GVSM9BidBundle(Map<IItem, Double> bids, int agentNum) {
		super(mapOf(bids)); 
		this.agentNum = agentNum;
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
	
	public int agentNum() {
		return this.agentNum;
	}
	
}