package brown.mechanism.channel;

import java.util.Map;

import brown.mechanism.bid.IGameAction;
import brown.user.agent.library.AbsAgent;

/**
 * Agent channel mediates communication between the agent and the server
 * by processing sent and received messages. 
 * @author acoggins
 *
 */
public interface IChannel {
  
	/**
	 * Gets the ID of the auction
	 * @return id
	 */
	public Integer getMarketID();
	
	/**
	 * Handles the double dispatch for trade request types
	 * @param agent
	 */
	public void dispatchMessage(AbsAgent agent);
	
	 /**
   * bids in the auction
   * @param agent
   * @param bid
   */
	public void bid(AbsAgent agent, IGameAction action);
	
}
