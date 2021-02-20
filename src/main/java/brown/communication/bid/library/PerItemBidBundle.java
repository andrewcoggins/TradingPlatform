package brown.communication.bid.library;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.util.Arrays;

import brown.communication.bid.IBidBundle;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.user.agent.library.localbid.IBidVector;

/**
 * A map from Tradeables to a Bids.
 * @author andrew
 *
 */
public class PerItemBidBundle extends AbsOneSidedBidBundle implements IBidBundle {
	public PerItemBidBundle() {
		super();
	}

	public PerItemBidBundle(IBidVector bids) {
		super(mapOf(bids)); 
	}

	private static Map<ICart, Double> mapOf(IBidVector bids) {
		Map<ICart, Double> m = new HashMap<>();
		for (IItem item : bids.goods()) {
			ICart cart = new Cart();
			cart.addToCart(item);
			m.put(cart, bids.getBid(item));
		}
		return m;
	}

}