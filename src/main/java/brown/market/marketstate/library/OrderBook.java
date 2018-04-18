package brown.market.marketstate.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import brown.bid.library.BidDirection;
import brown.bid.library.TwoSidedBid;
import brown.bidbundle.BundleType;
import brown.logging.Logging;
import brown.market.marketstate.IOrderBook;
import brown.messages.library.TradeMessage;

public class OrderBook implements IOrderBook {

  private PriorityQueue<BuyOrder> buyOrders;
  private PriorityQueue<SellOrder> sellOrders;
  
  public OrderBook() {
    this.buyOrders = new PriorityQueue<BuyOrder>();
    this.sellOrders = new PriorityQueue<SellOrder>();
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
  
  public void addTradeMessage(List<TradeMessage> tms){
    for (TradeMessage tm: tms){ 
      addTradeMessage(tm);
    }
  }
  
  public void addBuy(BuyOrder buy){
    this.buyOrders.add(buy);
  }
  
  public void addBuy(List<BuyOrder> buys){
    for (BuyOrder buy : buys){
      addBuy(buy);
    }
  }
  
  public void addSell(SellOrder sell){
    this.sellOrders.add(sell);
  }
  
  public void addSell(List<SellOrder> sells){
    for (SellOrder sell : sells){
      addSell(sell);
    }
  }

  public PriorityQueue<BuyOrder> getBuys(){
    return this.buyOrders;
  }
  
  public PriorityQueue<SellOrder> getSells(){
    return this.sellOrders;
  }  
  
  public void setBuys(PriorityQueue<BuyOrder> buys){
    this.buyOrders = buys;
  }
  
  public void setSells(PriorityQueue<SellOrder> sells){
    this.sellOrders = sells;
  }
  
  public OrderBook sanitize(Integer agent, Map<Integer, Integer> privateToPublic){
    OrderBook toReturn = new OrderBook();
    for (BuyOrder buy : this.buyOrders){
      toReturn.addBuy(buy.sanitize(agent, privateToPublic));
    }
    for (SellOrder sell : this.sellOrders){
      toReturn.addSell(sell.sanitize(agent, privateToPublic));
    }
    return toReturn;
  }
  
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


  // tests
  public static void main(String[] args) throws InterruptedException {
    OrderBook testbook = new OrderBook();
    for (int i=1; i<400;i++){
      testbook.addBuy(new BuyOrder(1,1,(double) i));
    }
    for (int i=1; i<400;i++){
      testbook.addSell(new SellOrder(1,1,(double) i));
    }
    Logging.log("SIZE OF buys: " + testbook.getBuys().size());
    OrderBook newbook = testbook.sanitize(200, 1, new HashMap<Integer,Integer>());
    Logging.log("SIZE OF buys: " + newbook.getBuys().size());
    
//    BuyOrder buy1 = new BuyOrder(1,1,15.);
//    BuyOrder buy2 = new BuyOrder(1,1,10.);
//    BuyOrder buy3 = new BuyOrder(1,1,20.);
//    BuyOrder buy4 = new BuyOrder(1,1,25.);
//    BuyOrder buy5 = new BuyOrder(3,1,15.);
//    testbook.addBuy(buy1);
//    testbook.addBuy(buy2);
//    testbook.addBuy(buy3);
//    testbook.addBuy(buy4);
//    testbook.addBuy(buy5);
//    
//    SellOrder Sell1 = new SellOrder(1,1,15.);
//    SellOrder Sell2 = new SellOrder(1,1,10.);
//    SellOrder Sell3 = new SellOrder(1,1,20.);
//    SellOrder Sell4 = new SellOrder(1,1,25.);
//    SellOrder Sell5 = new SellOrder(2,1,15.);
//    testbook.addSell(Sell1);
//    testbook.addSell(Sell2);
//    testbook.addSell(Sell3);
//    testbook.addSell(Sell4);
//    testbook.addSell(Sell5);
//    
//    Logging.log(testbook.getBuys().poll().toString());
//    Logging.log(testbook.getBuys().poll().toString());
//    Logging.log(testbook.getBuys().poll().toString());
//    Logging.log(testbook.getBuys().poll().toString());
//    Logging.log(testbook.getBuys().poll().toString());
//    
//    Logging.log(testbook.getSells().poll().toString());
//    Logging.log(testbook.getSells().poll().toString());
//    Logging.log(testbook.getSells().poll().toString());
//    Logging.log(testbook.getSells().poll().toString());
//    Logging.log(testbook.getSells().poll().toString());    
//    
//    Map<Integer,Integer> test = new HashMap<Integer,Integer>();
//    test.put(1,1);
//    test.put(2,2);
//    test.put(3,3);
//    test.put(4,4);
//    test.put(5,5);
//    test.put(6,6);
//    List<Integer> testList = new ArrayList<Integer>(test.values());
//    System.out.println(testList);
//    Logging.log(test.toString());
//    Collections.shuffle(testList);
//    System.out.println(testList);
//    Logging.log(test.toString());    
//    
//    PriorityQueue<SellOrder> sells = testbook.getSells();
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