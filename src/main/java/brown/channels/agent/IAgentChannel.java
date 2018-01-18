package brown.channels.agent;

import brown.accounting.library.Ledger;
import brown.agent.AbsAgent;

/*
 * Agents are exposed to client-side versions of markets through IMarket:
 * e.g., a OneSidedMarket, a TwoSidedMarket, or a NegotiationMarket. These
 * wrappers expose the current state of a market, as well as bid, buy, and sell
 * functionality. Agents can then simply call bid, buy, or sell on a Wrapper,
 * and the Wrapper will handle all communication with the server
 */
public interface IAgentChannel {
  
	/**
	 * Gets the ID of the auction
	 * @return id
	 */
	public Integer getAuctionID();
	
	/**
	 * Gets the ledger for this market
	 * @return ledger
	 */
	public Ledger getLedger();
	
	/**
	 * Handles the double dispatch for trade request types
	 * @param agent
	 */
	public void dispatchMessage(AbsAgent agent);
	
}
