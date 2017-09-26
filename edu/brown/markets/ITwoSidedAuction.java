package brown.markets;

import brown.valuable.library.Tradeable;

public interface ITwoSidedAuction extends IMarket {	
	/**
	 * Gets the full type of tradeable
	 * @return FullType
	 */
	public Tradeable getTradeableType();
}
