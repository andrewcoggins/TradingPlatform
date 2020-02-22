package brown.user.agent.library;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.auction.value.valuation.IGeneralValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;

public class LocalBid {
	public static interface IBidVector {
		public void setBid(IItem good, double bid);
		public double getBid(IItem good);
	}
	
	public static class BidVector implements IBidVector {
		private Map<IItem, Double> bids;
		public BidVector() {
			this.bids = new HashMap<>();
		}

		@Override
		public double getBid(IItem good) {
			return this.bids.get(good);
		}

		@Override
		public void setBid(IItem good, double bid) {
			this.bids.put(good, bid);
		}
	}
	
	public static interface ILinearPrices {
		public void setPrice(IItem good, double price);
		public double getPrice(IItem good);
	}
	
	public static class LinearPrices implements ILinearPrices {
		private Map<IItem, Double> prices;
		public LinearPrices() {
			this.prices = new HashMap<>();
		}

		@Override
		public double getPrice(IItem good) {
			return this.prices.get(good);
		}

		@Override
		public void setPrice(IItem good, double price) {
			this.prices.put(good, price);
		}
	}
	
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
