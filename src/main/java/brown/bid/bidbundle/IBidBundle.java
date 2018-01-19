package brown.bid.bidbundle;

import brown.bid.AbsBid;

/**
 * The IBidBundle interface permits the creation of arbitrary data structures
 * in which to hold bids and asks.
 */
public interface IBidBundle {
	
	/**
	 * returns some kind of bid.
	 * @return
	 */
  public AbsBid getBids();
  
	/**
	 * Identifies the type of the bundle
	 * @return bundleType
	 */
	public BundleType getType();	
	
}
