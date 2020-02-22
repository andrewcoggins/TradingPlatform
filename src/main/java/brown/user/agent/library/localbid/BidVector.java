package brown.user.agent.library.localbid;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import brown.auction.value.valuation.IGeneralValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;

public class BidVector implements IBidVector {
	private Map<IItem, Double> bids;
	public BidVector() {
		this.bids = new HashMap<>();
	}
	
	public BidVector(BidVector b) {
		this.bids = new HashMap<>(b.bids);
	}
	
	public BidVector(Collection<IItem> G, IGeneralValuation v) {
		this.bids = new HashMap<>();
		for (IItem g : G) {
			ICart c = new Cart();
			c.addToCart(g);
			this.bids.put(g, v.getValuation(c));
		}
	}

	@Override
	public double getBid(IItem good) {
		return this.bids.get(good);
	}

	@Override
	public void setBid(IItem good, double bid) {
		this.bids.put(good, bid);
	}

	@Override
	public Set<IItem> goods() {
		return new HashSet<>(this.bids.keySet());
	}

	@Override
	public IBidVector copy() {
		return new BidVector(this);
	}
}	
