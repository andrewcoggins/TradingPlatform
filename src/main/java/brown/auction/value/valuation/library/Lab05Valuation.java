package brown.auction.value.valuation.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import brown.auction.value.valuation.ISpecificValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;

public class Lab05Valuation extends AbsSparseValuation implements ISpecificValuation {

	private static final int NATL = 0, REG1 = 1, REG2 = 2, REG3 = 3;

	private int idx;
	private Map<String, Double> vals;

	public Lab05Valuation() {
		super();
		this.idx = 0;
		this.vals = new HashMap<>();
	}

	public Lab05Valuation(int idx) {
		super();
		this.idx = idx;
		this.vals = new HashMap<>();
		makeValuations();
	}

	// rand val up to
	private double rv(double max) {
		return max * Math.random();
	}

	private void putVals(int a, int b, int c, int d,
			int e, int f, int g, int h, int i) {
		this.vals.put("A", (a == 0) ? -1 : rv(a));
		this.vals.put("B", (b == 0) ? -1 : rv(b));
		this.vals.put("C", (c == 0) ? -1 : rv(c));
		this.vals.put("D", (d == 0) ? -1 : rv(d));
		this.vals.put("E", (e == 0) ? -1 : rv(e));
		this.vals.put("F", (f == 0) ? -1 : rv(f));
		this.vals.put("G", (g == 0) ? -1 : rv(g));
		this.vals.put("H", (h == 0) ? -1 : rv(h));
		this.vals.put("I", (i == 0) ? -1 : rv(i));
	}

	private void makeValuations() {
		this.vals.clear();
		this.vals.put("learning", (this.idx == 0) ? 1.0 : -1.0);

		this.vals.put("A", rv(25));
		this.vals.put("B", rv(25));
		this.vals.put("C", rv(75));
		this.vals.put("D", rv(75));
	}

	public Double getValuation(ICart cart) {
		double val = 0;
		Set<String> items = new HashSet<>();
		for (IItem item : cart.getItems()) {
			if (item.getName().equals("learning")) {
				return this.vals.get("learning");
			}

			val += this.vals.get(item.getName());
			items.add(item.getName());
		}

		if (items.contains("A") && items.contains("B")) {
			val *= 2.0;
		}
		
		if (items.contains("C") && items.contains("D")) {
			val *= 0.5;
		}
		
		if (items.size() == 4) {
			val = 0;
		}

		return val;
	}
}
