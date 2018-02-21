package brown.market.marketstate;

import java.util.List;

import brown.market.marketstate.library.BuyOrder;
import brown.market.marketstate.library.SellOrder;
import brown.messages.library.TradeMessage;

public interface IOrderBook {
  
  public void addTradeMessage(TradeMessage tm);
  
  public List<BuyOrder> getBuys();
  
  public List<SellOrder> getSells();
  
  public void setBuys(List<BuyOrder> buys);
  
  public void setSells(List<SellOrder> sells);
}