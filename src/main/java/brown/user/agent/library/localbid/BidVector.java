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
import brown.platform.item.library.Item;

public class BidVector implements IBidVector {
	private Map<String, Double> bids;
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
			this.bids.put(g.getName(), v.getValuation(c));
		}
	}

	@Override
	public double getBid(IItem good) {
		return this.bids.get(good.getName());
	}

	@Override
	public void setBid(IItem good, double bid) {
		this.bids.put(good.getName(), bid);
	}

	@Override
	public Set<IItem> goods() {
		Set<IItem> goods = new HashSet<>();
		for (String s : this.bids.keySet()) {
			goods.add(new Item(s));
		}
		return goods;
	}

	@Override
	public IBidVector copy() {
		return new BidVector(this);
	}
	
	@Override
	public String toString() {
		if (this.bids.isEmpty()) {
			return "{}";
		}
		
		String s = "BidVector: {";
		for (Map.Entry<String, Double> entry : this.bids.entrySet()) {
			s += "[" + entry.getKey() + " : " + entry.getValue() + "], ";
		}
		return s.substring(0, s.length() - 2) + "}";
	}

	@Override
	public boolean contains(IItem good) {
		return this.bids.containsKey(good.getName());
	}
}	
