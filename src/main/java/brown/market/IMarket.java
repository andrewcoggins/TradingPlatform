package brown.market;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import brown.messages.library.TradeMessage;
import brown.market.library.PrevStateInfo;
import brown.market.marketstate.library.Order;
import brown.messages.library.GameReportMessage;
import brown.messages.library.TradeRequestMessage;
import brown.value.valuation.IValuation;

public interface IMarket {
  
  /**
   * The platform supports multiple simultaneous markets.
   * This method returns this market's ID.
   */
  //rename getMarketID
  public Integer getID();
  
  /**
   * Constructs a trade request to send to a client.
   * How this trade request is constructed depends on the query rule.
   * @param ID - ID of the agent to which to send this trade request
   * @return a Trade Request, which will be sent over TCP by the server
   */
  public TradeRequestMessage constructTradeRequest(Integer ID);

  /**
   * Handles a trade. 
   * First, the activity rule determines if the trade is valid.
   * If so, it is handled according to the allocation and payment rules.
   * @param trade
   * @return ???
   */
  //what is the meaning of the boolean that is returned?
  public boolean handleBid(TradeMessage bid);
  
  
  //who asks for the list of orders?
  //the bank, so it can process them?
  //needs comments!
  public List<Order> constructOrders();
  
  
  //where is this game report created?
  public Map<Integer,GameReportMessage> constructReport();
  //shouldn't this be more like constructGameReport with this description:
  /**
   * Constructs a game report to send to a client.
   * How this game report is constructed depends on the IR rule.
   * @param ID - ID of the agent to which to send this game report
   * @return a Game Report, which will be sent over TCP by the server
   */
  

  /**
   * Per the (inner) termination condition, 
   * determines whether or not the inner market is over.
   * @return a boolean indicating whether or not the inner market is over.
   */
  public boolean isInnerOver();
  
  /**
   * Per the (outer) termination condition, 
   * determines whether or not the outer market is over.
   * @return a boolean indicating whether or not the outer market is over.
   */
  public boolean isOverOuter();
  
  public void tick();
  
  public void resetInnerMarket();

  PrevStateInfo constructSummaryState();

  // sets groupings
  public void setGroupings(List<Integer> groups);

  void record(Map<Integer,IValuation> privateVals) throws IOException;
  
  //close the market
  public void close();
  
}
