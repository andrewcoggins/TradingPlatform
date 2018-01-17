package brown.accounting.bidbundle.library;

import java.util.Map;
import java.util.Set;

import brown.accounting.BundleType;
import brown.accounting.MarketState;
import brown.accounting.bid.ComplexBid;
import brown.accounting.bidbundle.IBidBundle;
import brown.tradeable.library.Tradeable;

/**
 * A Complex Bid Bundle is a bid bundle that submits bids for vectors 
 * of goods. i.e. (a b c) = 1
 * @author acoggins
 *
 */
public class ComplexBidBundle implements IBidBundle {
  
	private final ComplexBid BIDS;
	private final BundleType BT;
	
	/**
	 * Empty constructor for kryo net
	 */
	public ComplexBidBundle() {
		this.BIDS = null;
		this.BT = null;
	}
	
	/**
	 * Use this constructor
	 * ComplexBidBundle uses Set<FullType>
	 * @param bid : agent's bid
	 * @param agent : agent ID
	 */
	public ComplexBidBundle(Map<Set<Tradeable>, MarketState> bid, Integer agent) {
		this.BIDS = new ComplexBid(bid);
		this.BT = BundleType.Complex;
	}

	@Override
	public double getCost() {
		double max = 0;
		for (MarketState b : this.BIDS.bids.values()) {
			max = Math.max(b.PRICE, max);
		}
		return max;
	}

	@Override
	public IBidBundle wipeAgent(Integer ID) {
		return new ComplexBidBundle(this.BIDS.bids, ID);
	}

	@Override
	public BundleType getType() {
		return this.BT;
	}
	
	public ComplexBid getBids() {
		return this.BIDS;
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
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ComplexBidBundle other = (ComplexBidBundle) obj;
    if (BIDS == null) {
      if (other.BIDS != null)
        return false;
    } else if (!BIDS.equals(other.BIDS))
      return false;
    if (BT != other.BT)
      return false;
    return true;
  }
	
}
