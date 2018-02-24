package brown.market.marketstate;

import java.util.PriorityQueue;

import brown.market.marketstate.library.BuyOrder;
import brown.market.marketstate.library.SellOrder;
import brown.messages.library.TradeMessage;

public interface IOrderBook {
  
  public void addTradeMessage(TradeMessage tm);
  
  public PriorityQueue<BuyOrder> getBuys();
  
  public PriorityQueue<SellOrder> getSells();
  
  public void setBuys(PriorityQueue<BuyOrder> buys);
  
  public void setSells(PriorityQueue<SellOrder> sells);
}