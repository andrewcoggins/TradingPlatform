package brown.market.marketstate.library;

import java.util.LinkedList;
import java.util.List;

import brown.bid.library.BidDirection;
import brown.bid.library.TwoSidedBid;
import brown.bidbundle.BundleType;
import brown.messages.library.TradeMessage;

public class OrderBook {
  private List<BuyOrder> buyOrders;
  private List<SellOrder> sellOrders;
  
  public OrderBook() {
    this.buyOrders = new LinkedList<BuyOrder>();
    this.sellOrders = new LinkedList<SellOrder>();
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
 }
