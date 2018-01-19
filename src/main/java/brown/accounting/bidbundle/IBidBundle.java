package brown.accounting.bidbundle;

import brown.accounting.bid.AbsBid;
import brown.accounting.bidbundle.library.BundleType;

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
	
  //MAKES NO SENSE!
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
	 * Identifies the type of the bundle
	 * @return bundleType
	 */
	public BundleType getType();	
	
}
