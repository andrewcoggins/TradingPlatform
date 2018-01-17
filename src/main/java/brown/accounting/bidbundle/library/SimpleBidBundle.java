package brown.accounting.bidbundle.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import brown.accounting.bid.SimpleBid;
import brown.accounting.bidbundle.IBidBundle;
import brown.tradeable.library.Tradeable;

/**
  * The built-in BidBundle is called SimpleBidBundle,
  * and holds one double. 
  */
public class SimpleBidBundle implements IBidBundle {
	private final Map<Tradeable, Double> BIDS;
	private final BundleType BT;
	
	/**
	 * For Kryo do not use
	 */
	public SimpleBidBundle() {
		this.BIDS = null;
		this.BT = null;
	}
	
	/**
	 * Actual constructor
	 * @param bid - agent's bid
	 * @param agent - agent ID
	 */
	public SimpleBidBundle(Map<Tradeable, Double> bids) {
		if (bids == null) {
			throw new IllegalArgumentException("Null bids");
		}
		this.BIDS = bids;
		this.BT = BundleType.Simple;
	}

  @Override
	public double getCost() {
		double total = 0;
		for (Double b : this.BIDS.values()) {
			total += b;
		}
		return total;
	}

	@Override
	public BundleType getType() {
		return this.BT;
	}
	
	public SimpleBid getBids() {
		return new SimpleBid(this.BIDS);
	}
	
	public Double getBid(Tradeable t) {
	  return BIDS.get(t);
	}
	
	@Override
	public String toString() {
		return "[" + this.BT + ": " + this.BIDS + "]";
	}
	
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((BIDS == null) ? 0 : BIDS.hashCode());
    result = prime * result + ((BT == null) ? 0 : BT.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    return(obj instanceof SimpleBidBundle && 
        ((SimpleBidBundle) obj).BIDS.equals(this.BIDS) &&
        ((SimpleBidBundle) obj).BT.equals(this.BT));
  }	
}
