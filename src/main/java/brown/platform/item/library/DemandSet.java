package brown.platform.item.library;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.util.Strings;
import org.spectrumauctions.sats.core.bidlang.xor.XORBid;
import org.spectrumauctions.sats.core.model.Bundle;
import org.spectrumauctions.sats.core.model.gsvm.GSVMLicense;

import brown.platform.item.ICart;
import brown.user.agent.library.SATSUtil;

public class DemandSet {
	private Set<GSVMLicense> items;
	private Bundle<GSVMLicense> bundle;
	private Map<Long, GSVMLicense> allGoods;

	// for kryo, do not use
	public DemandSet() {
		this.items = new HashSet<>();
		this.allGoods = null;
		this.bundle = new Bundle<>();
	}

	public void add(GSVMLicense item) {
		if (!this.contains(item)) {
			this.items.add(item);
			if (this.allGoods == null) {
				this.allGoods = SATSUtil.mapIDToGSVM18License(this.items.stream().iterator().next().getWorld());
			}
			this.bundle.add(item);
		}
	}

	public void remove(GSVMLicense item) {
		this.items.remove(item);
		this.bundle.remove(item);
	}

	public boolean contains(GSVMLicense item) {
		return this.items.contains(item);
	}

	public Set<GSVMLicense> items() {
		return new HashSet<>(this.items);
	}

	public int size() {
		return this.items.size();
	}

	public boolean isEmpty() {
		return this.items.isEmpty();
	}

	public ICart toCart() {
		ICart cart = new Cart();
		for (GSVMLicense license : this.items) {
			cart.addToCart(new Item(Long.toString(license.getId())));
		}
		return cart;
	}
	
	public Bundle<GSVMLicense> toBundle() {
		return new Bundle<>(this.bundle);
		
	}

	public String toString() {
		return "DemandSet: ["
				+ items.stream().map(GSVMLicense::toString).collect(Collectors.joining("\t"))
				+ "]";
	}

}
