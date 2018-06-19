package brown.bidbundle.library;

import java.util.Map;
import java.util.Map.Entry;

import brown.bid.interim.BidType;
import brown.bid.library.AuctionBid;
import brown.bidbundle.BundleType;
import brown.bidbundle.IBidBundle;
import brown.tradeable.ITradeable;

/**
  * an AuctionBidBundle maps from ITradeables to price 
  * and quantity, to specify a bid.
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
	public AuctionBidBundle(Map<ITradeable, BidType> bids) {
		if (bids == null) {
			throw new IllegalArgumentException("Null bids");
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
  public double getCost() {
    double cost = 0.;
    for (Entry<ITradeable,BidType> item: this.BIDS.bids.entrySet()){
      cost += item.getValue().price;
    }
    return cost;
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
