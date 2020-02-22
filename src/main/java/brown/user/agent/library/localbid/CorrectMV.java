package brown.user.agent.library.localbid;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.auction.value.valuation.IGeneralValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;

public class CorrectMV {
	public static double calcMarginalValue(Set<IItem> G, IItem good, IGeneralValuation v, IBidVector b, ILinearPrices p) {
		ICart bundle = new Cart();
		double payment = 0;
		for (IItem i2 : G) {
			if (i2.equals(good)) {
				continue;
			}
			
			double price = p.getPrice(i2);
			double bid = b.getBid(i2);
			if (bid > price) {
				bundle.addToCart(i2);
				payment += price;
			}
		}
		double uLose = (v.getValuation(bundle) - payment);
		bundle.addToCart(good);
		double uWin = (v.getValuation(bundle) - payment);

		return uWin - uLose;
	}
}
