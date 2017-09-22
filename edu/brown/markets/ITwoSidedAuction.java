package brown.markets;

import brown.assets.value.Tradeable;

public interface ITwoSidedAuction extends IMarket {	
	/**
	 * Gets the full type of tradeable
	 * @return FullType
	 */
	public Tradeable getTradeableType();
}
