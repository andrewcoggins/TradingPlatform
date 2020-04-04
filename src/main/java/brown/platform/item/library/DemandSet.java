package brown.platform.item.library;

import java.util.HashSet;
import java.util.Set;

public class DemandSet {
	private Set<String> items;

	// for kryo, do not use
	public DemandSet() {
		this.items = new HashSet<>();
	}
	
	public void add(String item) {
		this.items.add(item);
	}
	
	public void remove(String item) {
		this.items.remove(item);
	}
	
	public boolean contains(String item) {
		return this.items.contains(item);
	}
	
	public Set<String> items() {
		return new HashSet<>(this.items());
	}
	
	public int size() {
		return this.items.size();
	}
	
	public boolean isEmpty() {
		return this.items.isEmpty();
	}

}
