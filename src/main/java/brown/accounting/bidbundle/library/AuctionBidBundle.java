package brown.accounting.bidbundle.library;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.accounting.bid.AuctionBid;
import brown.accounting.bidbundle.IBidBundle;
import brown.tradeable.ITradeable;

/**
  * The built-in BidBundle is called AuctionBidBundle,
  * and holds one double. 
  */
public class AuctionBidBundle implements IBidBundle {
  
	private final AuctionBid BIDS;
	private final BundleType BT;
	
	/**
	 * For Kryo do not use
	 */
	public AuctionBidBundle() {
		this.BIDS = null;
		this.BT = null;
	}
	
	/**
	 * Actual constructor
	 * @param move - agent's bid
	 * @param agent - agent ID
	 */
	public AuctionBidBundle(Map<ITradeable, Double> bids) {
		if (bids == null) {
			throw new IllegalArgumentException("Null bids");
		}
		List<ITradeable> t = new LinkedList<ITradeable>();
		for (ITradeable b : t) {
		  bids.put(b, 1.0);
		}
		this.BIDS = new AuctionBid(bids);
		this.BT = BundleType.AUCTION;
	}

	 /**
   * @return - get the Bids in auction format (an AuctionBid wrapping a <ITradeable,Double> Map)
   */
	@Override
  public AuctionBid getBids() {
    return this.BIDS;
  }
  
	@Override
	public BundleType getType() {
		return this.BT;
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
    return(obj instanceof AuctionBidBundle && 
        ((AuctionBidBundle) obj).BIDS.equals(this.BIDS) &&
        ((AuctionBidBundle) obj).BT.equals(this.BT));
  }	
  
}
