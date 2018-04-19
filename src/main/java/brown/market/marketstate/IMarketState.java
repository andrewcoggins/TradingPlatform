package brown.market.marketstate;

import java.util.List;
import java.util.Map;

import brown.bidbundle.IBidBundle;
import brown.market.library.PrevStateInfo;
import brown.market.marketstate.library.Order;
import brown.market.marketstate.library.OrderBook;
import brown.messages.library.TradeMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.TradeRequestMessage;
import brown.tradeable.ITradeable;

/**
 * stores the internal state of a market as 
 * bidding is occurring.
 * 
 * @author acoggins
 */
public interface IMarketState {

    public Integer getID(); 
  
    public List<ITradeable> getTradeables();
    
    public void reset();
    
    public void tick(); 
    
    public int getTicks();  
    
    public long getTime();
    
    public void close(); 
    
    public boolean isOpen();

    //bids
    
    public void addBid(TradeMessage bid);
    
    public void clearBids();
    
    public List<TradeMessage> getBids(); 
    
    // orders (this is from the payment rule)
    
    public void clearOrders();
    
    //allocation and payment rule
    
    public Map<Integer,List<ITradeable>> getAllocation();
    
    public List<Order> getPayments();
    
    public void setAllocation(Map<Integer,List<ITradeable>> allocation);
    
    public void setPayments(List<Order> payment);
    
    
    //for Query Rules
    
    public TradeRequestMessage getTRequest(); 
    
    public void setTRequest(TradeRequestMessage t);
    
    public Map<ITradeable, Double> getIncrement(); 
    
    public void setIncrement(Map<ITradeable, Double> increment);
    
    public Double getFlatIncrement();
    
    public void setFlatIncrement(Double increment);    
    
    //activity rules
    
    public boolean getAcceptable();
    
    public void setAcceptable(boolean b);
    
    public Map<ITradeable,Double> getReserve();
    
    public void setReserve(Map<ITradeable,Double> o); 
    
    public Map<ITradeable, List<Integer>> getAltAlloc();
    
    public void setAltAlloc(Map<ITradeable, List<Integer>> o);
    
    //tcondition things
    public boolean getInnerOver();
    
    public void setInnerOver(boolean b); 
    
    public boolean getOuterOver();
    
    public void setOuterOver(boolean b); 
    
    public void incrementOuter();
    
    public Integer getOuterRuns();
    
    // Previous states
    public PrevStateInfo getPrevState();
    
    PrevStateInfo getSummaryState();
    
    void setSummaryState(PrevStateInfo prevState);
    
    //IR policy 
    public Map<Integer, List<GameReportMessage>> getReport();
    
    public void setReport(Map<Integer,List<GameReportMessage>> gameReport);

    // Groups
    public List<List<Integer>> getGroups();
    
    public void setGroups(List<List<Integer>> groups);

    public OrderBook getOrderBook();

    void setOrderBook(OrderBook book);
    
    //TODO: market update. 

}
