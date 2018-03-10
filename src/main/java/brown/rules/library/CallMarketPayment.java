package brown.rules.library;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import brown.bid.library.BidDirection;
import brown.bid.library.CancelBid;
import brown.bid.library.TwoSidedBid;
import brown.bidbundle.BundleType;
import brown.logging.Logging;
import brown.market.marketstate.IMarketState;
import brown.market.marketstate.library.BuyOrder;
import brown.market.marketstate.library.Order;
import brown.market.marketstate.library.OrderBook;
import brown.market.marketstate.library.SellOrder;
import brown.messages.library.TradeMessage;
import brown.rules.IPaymentRule;
import brown.tradeable.library.MultiTradeable;
import brown.tradeable.library.SimpleTradeable;

public class CallMarketPayment  implements IPaymentRule {

  @Override
  public void setOrders(IMarketState state) {
    Integer tradeableID  = ((SimpleTradeable) state.getTradeables().get(0)).ID;
    List<Order> orders = new LinkedList<Order>();
    
    OrderBook book = state.getOrderBook();
    List <TradeMessage> bids = state.getBids();
    List<TradeMessage> tradeBids = new LinkedList<TradeMessage>();
    List<TradeMessage> cancelBids = new LinkedList<TradeMessage>();
        
    for (TradeMessage bid : bids){
      if (bid.Bundle.getType() == BundleType.TWOSIDED){
        tradeBids.add(bid);
      } else if (bid.Bundle.getType() == BundleType.CANCEL){
        cancelBids.add(bid);
      } else {
        Logging.log("Invalid Bid Type: Neither a 2 sided bid or a cancelling bid");
      }
    }

    PriorityQueue<BuyOrder> buys = book.getBuys();
    PriorityQueue<SellOrder> sells = book.getSells();

    for (TradeMessage bid : cancelBids){
      Integer agent = bid.AgentID;
      CancelBid cancel = (CancelBid)  bid.Bundle.getBids();
      if (cancel.direction == BidDirection.BUY){
        buys.removeIf(buy -> buy.agent.equals(agent) && buy.price >= cancel.price);
      } else if (cancel.direction == BidDirection.SELL){
        sells.removeIf(sell -> sell.agent.equals(agent) && sell.price <= cancel.price);          
      } else {
        Logging.log("Unknown bid direction in cancel bid");
      }
    }
    
    for (TradeMessage bid : tradeBids){
        TwoSidedBid tsbid = (TwoSidedBid) bid.Bundle.getBids();
        int numToFill = tsbid.quantity;
        if (tsbid.direction == BidDirection.BUY) {
          boolean crossed = true;
          while (numToFill > 0 & sells.size() > 0 & crossed){
            // poll the best sell order
            SellOrder bestSell = sells.poll();
            if (bestSell.price <= tsbid.price) {
              double midpoint = (double) ((bestSell.price + tsbid.price) / 2.);            
              int quantity = Math.min(bestSell.quantity,tsbid.quantity);              
              // make an order an update number to fill
              orders.add(new Order(bid.AgentID,bestSell.agent, midpoint*quantity, quantity, new MultiTradeable(tradeableID, quantity)));                
              numToFill = Math.max(0, numToFill - bestSell.quantity); 
              if (bestSell.quantity > tsbid.quantity){
                sells.add(new SellOrder(bestSell.agent, bestSell.quantity - tsbid.quantity,bestSell.price));
              }
            } else {    
              crossed=false;
            }
          }
          // if OrderBook couldn't completely fill order, add it to unfilled orders          
          if (numToFill > 0 ){
            buys.add(new BuyOrder(bid.AgentID, numToFill, tsbid.price));
          }
        } else if (tsbid.direction == BidDirection.SELL) {
          boolean crossed = true;          
          while (numToFill > 0 & buys.size() > 0 & crossed){   
            // poll the best sell order
            BuyOrder bestBuy = buys.poll();
            if (bestBuy.price >= tsbid.price){
              double midpoint = (double) ((bestBuy.price + tsbid.price) / 2.);            
              int quantity = Math.min(bestBuy.quantity,tsbid.quantity);              
              // make an order an update number to fill
              orders.add(new Order(bestBuy.agent,bid.AgentID, midpoint*quantity, quantity,new MultiTradeable(tradeableID, quantity)));                
              numToFill = Math.max(0, numToFill - bestBuy.quantity);    
              if (bestBuy.quantity > tsbid.quantity){
                sells.add(new SellOrder(bestBuy.agent, bestBuy.quantity - tsbid.quantity,bestBuy.price));
              }              
            } else {     
              crossed=false;
            }            
          }
          // if OrderBook couldn't completely fill order, add it to unfilled orders          
          if (numToFill > 0 ){   
            sells.add(new SellOrder(bid.AgentID, numToFill, tsbid.price));
          }          
        } else {
          Logging.log("Unknown bid direction in two sided bid");
        }               
    }
    // Now update orderbook
    book.setBuys(buys);
    book.setSells(sells);
    
    state.setOrderBook(book);
    state.setPayments(orders);
  }
  
  @Override
  public void reset() {
  }

}
