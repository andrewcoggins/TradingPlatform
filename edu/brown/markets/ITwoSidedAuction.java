package brown.markets;

import brown.valuable.library.Tradeable;

/*
 * Implements IMarket for Two-Sided auctions
 */
public interface ITwoSidedAuction extends IMarket {	
	/**
	 * Gets the full type of tradeable
	 * @return FullType
	 */
	public Tradeable getTradeableType();
}
