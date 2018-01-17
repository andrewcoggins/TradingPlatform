package brown.accounting.bidbundle;

import java.util.Comparator;

import brown.accounting.BundleType;
import brown.accounting.bid.AbsBid;

/**
 * The IBidBundle interface permits the creation of arbitrary data structures
 * in which to hold bids and asks.
 */

//AMY: these comments belong elsewhere
/*
 * Whenever the server receives a valid BidBundle, the corresponding
 * market handles it (i.e., it updates its current state). 
 * 
 * If a trade clears, depending on the market's rule,
 * the server can send a MarketUpdate (i.e., an updated ledger) to all
 * agents and a BankUpdate to the agents directly involved in the trade.
 * 
 * @author andrew
 */
public interface IBidBundle {
	
	/**
	 * Gets the aggregate cost of the bid for verification purposes
	 * @return total cost
	 */
	double getCost();
	
	/**
	 * returns some kind of bid.
	 * @return
	 */
  public AbsBid getBids();
  
	/**
	 * Removes agent ID
	 * @return BidBundle w/o agent ID
	 */
	IBidBundle wipeAgent(Integer ID);

	/**
	 * Identifies the type of the bundle
	 * @return bundleType
	 */
	public BundleType getType();
	
	/**
	 * comparator for BidBundles
	 */
	public static class BidBundleComparator implements Comparator<IBidBundle> {
		@Override
		public int compare(IBidBundle arg0, IBidBundle arg1) {
			return new Double(arg0.getCost()).compareTo(new Double(arg1.getCost()));
		}
	}
	
}
