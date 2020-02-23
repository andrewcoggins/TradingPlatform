package brown.user.agent.library.localbid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import brown.platform.item.IItem;

public class LinearPrices implements ILinearPrices {
	private Map<IItem, Double> prices;
	public LinearPrices() {
		this.prices = new HashMap<>();
	}
	
	public LinearPrices(LinearPrices p) {
		this.prices = new HashMap<>(p.prices);
	}

	@Override
	public double getPrice(IItem good) {
		return this.prices.get(good);
	}

	@Override
	public void setPrice(IItem good, double price) {
		this.prices.put(good, price);
	}

	@Override
	public Set<IItem> goods() {
		return new HashSet<>(this.prices.keySet());
	}

	@Override
	public ILinearPrices copy() {
		return new LinearPrices(this);
	}
	
	@Override
	public String toString() {
		if (this.prices.isEmpty()) {
			return "{}";
		}
		
		String s = "LinearPrices: {";
		for (Map.Entry<IItem, Double> entry : this.prices.entrySet()) {
			s += "[" + entry.getKey().getName() + " : " + entry.getValue() + "], ";
		}
		return s.substring(0, s.length() - 2) + "}";
	}
}