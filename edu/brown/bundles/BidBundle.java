package brown.bundles;

import java.util.Comparator;

/**
 * The BidBundle interface permits the creation of an arbitrary data structure
 * in which to hold bids (and asks).
 * Agents do not need to know what BidBundle is used because the auctions
 * expose methods that take in the required information.
 * 
 *  Whenever the server receives a valid BidBundle, the corresponding
 *  market handles it. If a trade clears, depending on the market’s rule,
 *  the server can send a MarketUpdate (i.e., an updated ledger) to all
 *  agents and a BankUpdate to the agents directly involved in the trade;
 *  if not, the market updates its current state.
 * @author andrew
 *
 */
public interface BidBundle {
	
	/**
	 * Gets the aggregate cost of the bid
	 * for verification purposes
	 * @return total cost
	 */
	double getCost();
	
	/**
	 * Removes agent ID
	 * @return BidBundle w/o agent ID
	 */
	BidBundle wipeAgent(Integer ID);

	/**
	 * Identifies the type of the bundle
	 * @return bundleType
	 */
	public BundleType getType();
	
	/**
	 * comparator for BidBundles.
	 *
	 */
	public static class BidBundleComparator implements Comparator<BidBundle> {

		@Override
		public int compare(BidBundle arg0, BidBundle arg1) {
			return new Double(arg0.getCost()).compareTo(new Double(arg1.getCost()));
		}
		
	}
}
