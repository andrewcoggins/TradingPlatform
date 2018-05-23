package brown.market.twosided;

import java.util.Map;
import java.util.PriorityQueue;

import brown.messages.library.TradeMessage;

/**
 * The order book for two-sided auctions tracks the 
 * buy and sell orders placed within a two-sided market. 
 * @author acoggins
 *
 */
public interface IOrderBook {
  
  /**
   * adds a trade message to the orderbook. 
   * @param tm a TradeMessage
   */
  public void addTradeMessage(TradeMessage tm);
  
  /**
   * Get all buy orders within the orderbook. 
   * @return A priority Queue of BuyOrders.
   */
  public PriorityQueue<BuyOrder> getBuys();
  
  /**
   * Get all sell orders within the orderbook. 
   * @return A priority Queue of SellOrders.
   */
  public PriorityQueue<SellOrder> getSells();
  
  /**
   * Set all BuyOrders in the OrderBook. 
   * @return A priority Queue of BuyOrders.
   */
  public void setBuys(PriorityQueue<BuyOrder> buys);
  
  /**
   * Set all SellOrders in the OrderBook. 
   * @return A priority Queue of SellOrders.
   */
  public void setSells(PriorityQueue<SellOrder> sells);
  
  /**
   * Sanitizes for agent use.
   * @param n
   * @param agent
   * @param privateToPublic
   * @return
   */
  public IOrderBook sanitize(Integer n, Integer agent, Map<Integer, Integer> privateToPublic); 
}