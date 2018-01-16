package brown.market;

import java.util.List;

import brown.accounting.Order;
import brown.messages.library.TradeMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.TradeRequestMessage;

public interface IMarket {
  
  /*
   * The platform supports multiple markets. This
   * function gets the id of this particular market. 
   */
  public Integer getID();
  
  /**
   * Constructs a trade request to send to a client. How this trade
   * request is constructed depends on the query rule.
   * @param ID
   * Private ID of the client to which this is sent.
   * @return
   * a Trade Request, which will be sent over TCP by the server.
   */
  public TradeRequestMessage constructTradeRequest(Integer ID);

  /**
   * per the (inner) termination condition, determines whether or not
   * the game is over.
   * @return
   * a boolean for whether or not the game is over.
   */
  public boolean isOver();
  
  /**
   * per the (outer) termination condition, determines whether or 
   * not the game is over.
   * @return
   * a boolean for whether or not the game is over.
   */
  public boolean isOverOuter();
  
  /**
   * Handles a bid. The activity rule determines 
   * @param bid
   * @return
   */
  public boolean handleBid(TradeMessage bid);
  
  public List<Order> getOrders();
  
  public GameReportMessage getReport();
  
  public void tick(long time);
  
  public void clearState();
  
}