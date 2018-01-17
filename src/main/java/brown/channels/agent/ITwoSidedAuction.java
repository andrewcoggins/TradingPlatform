package brown.channels.agent;

import brown.tradeable.library.Tradeable;


/*
 * Implements IMarket for Two-Sided auctions
 */
public interface ITwoSidedAuction extends IAgentChannel {	
	/**
	 * Gets the full type of tradeable
	 * @return FullType
	 */
	public Tradeable getTradeableType();
}
