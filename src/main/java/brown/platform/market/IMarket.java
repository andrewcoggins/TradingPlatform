package brown.platform.market;

import java.util.List;
import java.util.Set;

import brown.auction.marketstate.IMarketPublicState;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.platform.accounting.IAccountUpdate;

/**
 * IMarket is the interface for the Market class. 
 * @author andrewcoggins
 *
 */
public interface IMarket {

  /**
   * Get the ID of the market. 
   * @return
   */
  public Integer getMarketID();

  /**
   * Construct a trade request for the market. 
   * @param agentID
   * ID of the agent the trade request is intended for.
   * @return
   * TradeRequestMessage. 
   */
  public ITradeRequestMessage constructTradeRequest(Integer agentID);

  /**
   * Process a ITradeMessage as a bid. 
   * @param bid
   * @return
   * boolean determining whether or not the bid was accepted. 
   */
  public boolean processBid(ITradeMessage bid);
  
  /**
   * Construct account updates once the market has completed. 
   * @return
   */
  public List<IAccountUpdate> constructAccountUpdates();

  /**
   * indicate that a timestep has passed if the auction is discrete. 
   */
  public void tick();
  
  
  public Integer getTimestep(); 
  
  
  /**
   * Set/update reserve price(s) for the auction. 
   */
  public void setReserves(); 
  
  /**
   * update some inner information of the market. 
   * Occurs per Inner IRPolicy
   */
  public void updateInnerInformation(); 
  
  /**
   * update some outer information of the market. 
   * Occurs per IR Policy. 
   */
  public void updateOuterInformation(); 
  
  /**
   * clear cache of bids within the market. 
   */
  public void clearBidCache();

  /**
   * signals whether or not the market is open. 
   * @return
   */
  public boolean isOpen(); 
  
  /**
   * get the MarketPublicState of the market. 
   * this represents the information allowed by the 
   * IR Policies. 
   * @return
   */
  public IMarketPublicState getPublicState(); 
  
  /**
   * get the complete MarketPublicState of the market, unredacted by any IR policies. 
   * @return
   */
  public IMarketPublicState getUnredactedPublicState(); 
  
  public void updateTradeHistory(); 
  
  public Set<Integer> getMarketAgents(); 
  
}
