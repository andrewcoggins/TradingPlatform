package brown.platform.market;

import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.TradeRequestMessage;
import brown.platform.accounting.IAccountUpdate;

/**
 * The market receives bids from the agents via the server
 * and processes them according to a series of rules to produce outcomes. 
 * @author acoggins
 *
 */
public interface IMarket {
  
/**
 * Gets this market's ID
 * @return market ID
 */
  public Integer getMarketID();
  
  /**
   * Constructs a trade request to send to a client.
   * How this trade request is constructed depends on the query rule.
   * @param ID - ID of the agent to which to send this trade request
   * @return a Trade Request, which will be sent over TCP by the server
   */
  public TradeRequestMessage constructTradeRequest(Integer agentID);

  /**
   * Handles a trade. 
   * First, the activity rule determines if the trade is valid.
   * If so, it is handled according to the allocation and payment rules.
   * @param trade
   * @return a boolean representing whether or not the bid was accepted
   * by the activity rule. 
   */
  public boolean processBid(ITradeMessage bid);
  
  /**
   * applies the allocation and payment rules to all bids
   * in a particular round. This produces orders that update
   * the agents' accounts.
   * @return a list of orders that update the agents' accounts.
   */
  public List<IAccountUpdate> constructOrders();
  
  
  /**
   * Constructs game reports to send to agents at the end of 
   * an auction.
   * How this game report is constructed depends on the IR policy.
   * @param ID - ID of the agent to which to send this game report
   * @return a Game Report, which will be sent over TCP by the server
   */
  public Map<Integer,List<IInformationMessage>> constructReport();

  
  /**
   * increments the time of the market.
   */
  public void tick();
  
  /**
   * Sets the reserve prices of the auction.
   */
  public void setReserves(); 
  
  /**
   * clears all information stored in market state
   * and rules.
   */
  public void clearBidCache();

  
  /**
   * Marks the market as closed so it can be closed in the MarketManager.
   */
  public void close();
  
  /**
   * @return whether or not the market has been closed.
   */
  public boolean isOpen(); 
  
  
  public IMarketPublicState getPublicState(); 
  
}
