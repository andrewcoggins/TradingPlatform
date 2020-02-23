package brown.user.agent.library.localbid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.ArrayList;

import brown.auction.value.valuation.IGeneralValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Item;
import brown.user.agent.library.localbid.ILinearPrices;
import brown.user.agent.library.localbid.LinearPrices;

public class SampleValuations {
	private static final Map<IItem, Double> SINGLE_ITEM_VALS = new HashMap<>();
	
	static {
		SINGLE_ITEM_VALS.put(new Item("A"), 70.0);
		SINGLE_ITEM_VALS.put(new Item("B"), 55.0);
		SINGLE_ITEM_VALS.put(new Item("C"), 85.0);
		SINGLE_ITEM_VALS.put(new Item("D"), 50.0);
		SINGLE_ITEM_VALS.put(new Item("E"), 15.0);
		SINGLE_ITEM_VALS.put(new Item("F"), 65.0);
		SINGLE_ITEM_VALS.put(new Item("G"), 80.0);
		SINGLE_ITEM_VALS.put(new Item("H"), 90.0);
		SINGLE_ITEM_VALS.put(new Item("I"), 75.0);
		SINGLE_ITEM_VALS.put(new Item("J"), 60.0);
		SINGLE_ITEM_VALS.put(new Item("K"), 40.0);
		SINGLE_ITEM_VALS.put(new Item("L"), 80.0);
		SINGLE_ITEM_VALS.put(new Item("M"), 90.0);
		SINGLE_ITEM_VALS.put(new Item("N"), 25.0);
		SINGLE_ITEM_VALS.put(new Item("O"), 65.0);
		SINGLE_ITEM_VALS.put(new Item("P"), 70.0);
	}
	
	public static Set<IItem> G() {
		return new HashSet<>(SINGLE_ITEM_VALS.keySet());
	}
	
	public static ILinearPrices genPriceVector() {
		ILinearPrices p = new LinearPrices();
		for (IItem good : G()) {
			p.setPrice(good, Math.random() * 15 + SINGLE_ITEM_VALS.get(good) - 7.5);
		}
		return p;
	}
	
	public static final IGeneralValuation ADDITIVE_VALUATION = new IGeneralValuation() {
		@Override
		public Double getValuation(ICart c) {
			double val = 0.0;
			
			for (IItem good : c.getItems()) {
				val += SINGLE_ITEM_VALS.get(good);
			}
			
			return val;
		}
	};
	
	public static final IGeneralValuation COMPLEMENT_VALUATION = new IGeneralValuation() {
		@Override
		public Double getValuation(ICart c) {
			double val = 0.0;
			
			for (IItem good : c.getItems()) {
				val += SINGLE_ITEM_VALS.get(good);
			}
			
			val *= Math.pow(1.05, Math.max(c.getItems().size() - 1, 0));
			
			return val;
		}
	};
	
	public static final IGeneralValuation SUBSTITUTE_VALUATION = new IGeneralValuation() {
		@Override
		public Double getValuation(ICart c) {
			double val = 0.0;
			
			for (IItem good : c.getItems()) {
				val += SINGLE_ITEM_VALS.get(good);
			}
			
			val *= Math.pow(0.95, Math.max(c.getItems().size() - 1, 0));
			
			return val;
		}
	};
	
	private static final Map<IItem[], Double> COMPLEMENTS = new HashMap<>();
	private static final Map<IItem[], Double> SUBSTITUTES = new HashMap<>();
	
	static {
		List<IItem> lst = new ArrayList<>(SINGLE_ITEM_VALS.keySet());
		for (int k = 2; k <= SINGLE_ITEM_VALS.size(); k++) {
			Iterator<int[]> it = CombinatoricsUtils.combinationsIterator(SINGLE_ITEM_VALS.size(), k);
			while (it.hasNext()) {
				int[] inds = it.next();
				IItem[] key = new IItem[k];
				double amt = 1 - (Math.random() * 0.001);
				int j = 0;
				for (int i : inds) {
					key[j++] = lst.get(i);
				}
				if (Math.random() < 0.5) {
					SUBSTITUTES.put(key, amt);
				} else {
					COMPLEMENTS.put(key, 1.0 / amt);
				}
			}
		}
	}
	
	public static final IGeneralValuation COMPLICATED_VALUATION = new IGeneralValuation() {
		
		@Override
		public Double getValuation(ICart c) {
			double val = 0.0;
			
			for (IItem good : c.getItems()) {
				val += SINGLE_ITEM_VALS.get(good);
			}
			
			Set<IItem> itemSet = new HashSet<>(c.getItems());
			for (IItem[] key : COMPLEMENTS.keySet()) {
				boolean good = true;
				for (IItem it : key) {
					if (!itemSet.contains(it)) {
						good = false;
						break;
					}
				}
				if (good) {
					val *= COMPLEMENTS.get(key);
				}
			}
			
			for (IItem[] key : SUBSTITUTES.keySet()) {
				boolean good = true;
				for (IItem it : key) {
					if (!itemSet.contains(it)) {
						good = false;
						break;
					}
				}
				if (good) {
					val *= SUBSTITUTES.get(key);
				}
			}
			
			return val;
		}
	};
}
