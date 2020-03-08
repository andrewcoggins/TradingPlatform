package brown.user.agent.library.localbid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import brown.platform.item.IItem;
import brown.platform.item.library.Item;

public class LinearPrices implements ILinearPrices {
	private Map<String, Double> prices;
	public LinearPrices() {
		this.prices = new HashMap<>();
	}
	
	public LinearPrices(LinearPrices p) {
		this.prices = new HashMap<>(p.prices);
	}

	@Override
	public double getPrice(IItem good) {
		return this.prices.get(good.getName());
	}

	@Override
	public void setPrice(IItem good, double price) {
		this.prices.put(good.getName(), price);
	}

	@Override
	public Set<IItem> goods() {
		Set<IItem> goods = new HashSet<>();
		for (String s : this.prices.keySet()) {
			goods.add(new Item(s));
		}
		return goods;
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
		for (Map.Entry<String, Double> entry : this.prices.entrySet()) {
			s += "[" + entry.getKey() + " : " + entry.getValue() + "], ";
		}
		return s.substring(0, s.length() - 2) + "}";
	}
	
	@Override
	public boolean contains(IItem good) {
		return this.prices.containsKey(good.getName());
	}

	@Override
	public int size() {
		return this.prices.size();
	}
}