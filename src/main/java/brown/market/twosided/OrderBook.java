package brown.market.twosided;

import java.util.Map;
import java.util.PriorityQueue;

import brown.bid.library.BidDirection;
import brown.bid.library.TwoSidedBid;
import brown.bidbundle.BundleType;
import brown.messages.library.TradeMessage;

/**
 * The OrderBook tracks buys and sells in a two-sided market.
 * @author acoggins
 *
 */
public class OrderBook implements IOrderBook {

  private PriorityQueue<BuyOrder> buyOrders;
  private PriorityQueue<SellOrder> sellOrders;
  
  /**
   * The OrderBook is initially empty.
   */
  public OrderBook() {
    this.buyOrders = new PriorityQueue<BuyOrder>();
    this.sellOrders = new PriorityQueue<SellOrder>();
  }
  
  @Override
  public void addTradeMessage(TradeMessage tm){
    if (tm.Bundle.getType() == BundleType.TWOSIDED){
      TwoSidedBid bid = (TwoSidedBid) tm.Bundle.getBids();
      if (bid.direction == BidDirection.BUY){
        buyOrders.add(new BuyOrder(tm.AgentID,bid.quantity,bid.price));
      } else if (bid.direction == BidDirection.SELL){
        sellOrders.add(new SellOrder(tm.AgentID,bid.quantity,bid.price));      
      }      
    }
  }
  
  @Override
  public PriorityQueue<BuyOrder> getBuys() {
    return this.buyOrders;
  }
  
  @Override
  public PriorityQueue<SellOrder> getSells() {
    return this.sellOrders;
  }  
  
  @Override
  public void setBuys(PriorityQueue<BuyOrder> buys){
    this.buyOrders = buys;
  }
  
  @Override
  public void setSells(PriorityQueue<SellOrder> sells){
    this.sellOrders = sells;
  }
  
  @Override
  public OrderBook sanitize(Integer n, Integer agent, Map<Integer, Integer> privateToPublic){
    OrderBook toReturn = new OrderBook();
    OrderBook temp = new OrderBook();
    int count = 0;
    while (count < n && this.buyOrders.peek() != null){
      BuyOrder buy = this.buyOrders.poll();
      temp.addBuy(buy);
      toReturn.addBuy(buy.sanitize(agent, privateToPublic));
      count++;
    }
    count = 0;
    while (count < n && this.sellOrders.peek() != null){
      SellOrder sell = this.sellOrders.poll();
      temp.addSell(sell);
      toReturn.addSell(sell.sanitize(agent, privateToPublic));
      count++;
    }
    
    for (BuyOrder buy: temp.buyOrders){
      this.addBuy(buy);
    }
    for (SellOrder sell: temp.sellOrders){
      this.addSell(sell);
    }  
    return toReturn;
  }
  
  private void addBuy(BuyOrder buy) {
    this.buyOrders.add(buy);
  }
  
  private void addSell(SellOrder sell) {
    this.sellOrders.add(sell);
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((buyOrders == null) ? 0 : buyOrders.hashCode());
    result =
        prime * result + ((sellOrders == null) ? 0 : sellOrders.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    OrderBook other = (OrderBook) obj;
    if (buyOrders == null) {
      if (other.buyOrders != null)
        return false;
    } else if (!buyOrders.equals(other.buyOrders))
      return false;
    if (sellOrders == null) {
      if (other.sellOrders != null)
        return false;
    } else if (!sellOrders.equals(other.sellOrders))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "OrderBook [buyOrders=" + buyOrders + ", sellOrders=" + sellOrders
        + "]";
  }
 }