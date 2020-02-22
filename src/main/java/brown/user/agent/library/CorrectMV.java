package brown.user.agent.library;

import java.util.Map;
import java.util.Set;

import brown.auction.value.valuation.IGeneralValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;

public class CorrectMV {
	public static double calcMarginalValue(Set<IItem> G, IItem good, IGeneralValuation v, Map<IItem, Double> p) {
		ICart bundle = new Cart();
		double payment = 0;
		for (IItem i2 : G) {
			if (i2.equals(good)) {
				continue;
			}
			
			ICart i2Cart = new Cart();
			i2Cart.addToCart(i2);
			
			double price = p.get(i2);
			double valuation = v.getValuation(i2Cart);
			if (price < valuation) {
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
