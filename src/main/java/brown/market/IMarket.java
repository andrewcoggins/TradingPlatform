package brown.market;

import java.util.List;

import brown.accounting.Order;
import brown.messages.library.Bid;
import brown.messages.library.GameReport;
import brown.messages.library.TradeRequest;

public interface IMarket {
  
  public Integer getID();
  
  public TradeRequest constructTradeRequest(Integer ID);

  public boolean isOver();
  
  public boolean isOverOuter();
  
  public boolean handleBid(Bid bid);
  
  public List<Order> getOrders();
  
  public GameReport getReport();
  
  public void tick(long time);
  
  public void clearState();
  
}