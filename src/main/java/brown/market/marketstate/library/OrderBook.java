package brown.market.marketstate.library;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import brown.bid.library.BidDirection;
import brown.bid.library.TwoSidedBid;
import brown.bidbundle.BundleType;
import brown.logging.Logging;
import brown.market.marketstate.IOrderBook;
import brown.messages.library.TradeMessage;

public class OrderBook implements IOrderBook {

  private List<BuyOrder> buyOrders;
  private List<SellOrder> sellOrders;
  
  public OrderBook() {
    this.buyOrders = new LinkedList<BuyOrder>();
    this.sellOrders = new LinkedList<SellOrder>();
  }
  
  public OrderBook(List<BuyOrder> buys, List<SellOrder> sells) {
    this.buyOrders = buys;
    this.sellOrders = sells;
  }  
  
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
  
  public List<BuyOrder> getBuys(){
    return this.buyOrders;
  }
  
  public List<SellOrder> getSells(){
    return this.sellOrders;
  }  
  
  public void setBuys(List<BuyOrder> buys){
    this.buyOrders = buys;
  }
  
  public void setSells(List<SellOrder> sells){
    this.sellOrders = sells;
  } 
  
  public static void main(String[] args) throws InterruptedException {
    OrderBook testbook = new OrderBook();
    BuyOrder buy1 = new BuyOrder(1,1,15.);
    BuyOrder buy2 = new BuyOrder(1,1,10.);
    BuyOrder buy3 = new BuyOrder(1,1,20.);
    BuyOrder buy4 = new BuyOrder(1,1,25.);
    BuyOrder buy5 = new BuyOrder(3,1,15.);
    List<BuyOrder> buylist = new LinkedList<BuyOrder>();
    buylist.add(buy1);
    buylist.add(buy2);
    buylist.add(buy3);
    buylist.add(buy4);
    buylist.add(buy5);
    
    SellOrder Sell1 = new SellOrder(1,1,15.);
    SellOrder Sell2 = new SellOrder(1,1,10.);
    SellOrder Sell3 = new SellOrder(1,1,20.);
    SellOrder Sell4 = new SellOrder(1,1,25.);
    SellOrder Sell5 = new SellOrder(2,1,15.);
    List<SellOrder> Selllist = new LinkedList<SellOrder>();
    Selllist.add(Sell1);
    Selllist.add(Sell2);
    Selllist.add(Sell3);
    Selllist.add(Sell4);
    Selllist.add(Sell5);    
    
    Logging.log(buylist.toString());
    Logging.log(Selllist.toString());
    Collections.sort(buylist,(h1, h2) -> -1*(h1.price.compareTo(h2.price)));
    Collections.sort(Selllist, (h1, h2) -> h1.price.compareTo(h2.price));
    Logging.log(buylist.toString());
    Logging.log(Selllist.toString());   
    
    testbook.setBuys(buylist);
    testbook.setSells(Selllist);   
    
    Logging.log(testbook.toString());
    
    List<BuyOrder> buys = testbook.getBuys();
    buys.add(new BuyOrder(5,1,100.));
    
    Logging.log(testbook.toString());
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