package brown.markets;

import java.util.List;

import brown.assets.accounting.Ledger;
import brown.assets.accounting.Order;
import brown.messages.auctions.Bid;
import brown.messages.markets.GameReport;
import brown.messages.markets.TradeRequest;

public interface IMarket {
  
  public Integer getID();
  
  public TradeRequest constructTradeRequest(Integer ID, Ledger ledger);

  public boolean isOver();
  
  public boolean handleBid(Bid bid);
  
  public List<Order> getOrders();
  
  public GameReport getReport();
  
  public void tick(long time);
  
}