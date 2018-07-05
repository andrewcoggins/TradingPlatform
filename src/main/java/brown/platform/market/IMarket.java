package brown.platform.market;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import brown.auction.prevstate.PrevStateInfo;
import brown.auction.value.valuation.IValuation;
import brown.platform.accounting.Order;
import brown.platform.messages.GameReportMessage;
import brown.platform.messages.TradeMessage;
import brown.platform.messages.TradeRequestMessage;

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
  public TradeRequestMessage constructTradeRequest(Integer ID);

  /**
   * Handles a trade. 
   * First, the activity rule determines if the trade is valid.
   * If so, it is handled according to the allocation and payment rules.
   * @param trade
   * @return a boolean representing whether or not the bid was accepted
   * by the activity rule. 
   */
  public boolean handleBid(TradeMessage bid);
  
  /**
   * applies the allocation and payment rules to all bids
   * in a particular round. This produces orders that update
   * the agents' accounts.
   * @return a list of orders that update the agents' accounts.
   */
  public List<Order> constructOrders();
  
  
  /**
   * Constructs game reports to send to agents at the end of 
   * an auction.
   * How this game report is constructed depends on the IR policy.
   * @param ID - ID of the agent to which to send this game report
   * @return a Game Report, which will be sent over TCP by the server
   */
  public Map<Integer,List<GameReportMessage>> constructReport();

  /**
   * Per the (inner) termination condition, 
   * determines whether or not the inner market is over.
   * @return a boolean indicating whether or not the inner market is over.
   */
  public boolean isInnerOver();
  
  /**
   * Per the (outer) termination condition, 
   * determines whether or not the outer market is over.
   * @return a boolean indicating whether or not the outer market is over.
   */
  public boolean isOverOuter();
  
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
  public void resetInternalState();

  /**
   * Constructs a summary state that can be used in markets opened 
   * after this market. What is stored in the summary state depends
   * on IR policy
   * @return a PrevStateInfo containing some information 
   * from this market.
   */
  PrevStateInfo constructSummaryState();

  /**
   * Some auctions require that agents compete only in groups of a set 
   * size. How groupings are determined in a market depends on the 
   * grouping rule.
   * @param the private IDs of all agents. 
   */
  public void setGroupings(List<Integer> allAgents);

  /**
   * records some information from this market by writing it to a file. 
   * What information is recording depends 
   * @param privateVals
   * @throws IOException
   */
  public void record(Map<Integer,IValuation> privateVals) throws IOException;
  
  /**
   * Marks the market as closed so it can be closed in the MarketManager.
   */
  public void close();
  
  /**
   * @return whether or not the market has been closed.
   */
  public boolean isOpen(); 
}
