package brown.auction.marketstate;

import java.util.List;
import java.util.Map;

import brown.communication.messages.IInformationMessage;
import brown.communication.messages.library.TradeMessage;
import brown.communication.messages.library.TradeRequestMessage;
import brown.platform.accounting.library.AccountUpdate;
import brown.platform.tradeable.ITradeable;

/**
 * Stores the internal state of a market as it runs. 
 * Consists of a series of getters and setters 
 * for the various fields of the market state.
 * 
 * @author acoggins
 */
public interface IMarketState {

    // part of Market, not state!
    public Integer getID(); 
  
    // part of Market, not state!
    public List<ITradeable> getTradeables();
    
    // part of Market, not state!
    public void addBid(TradeMessage bid);
    public void clearBids();
    public List<TradeMessage> getBids(); 
    
    // Move to Market -- b/c static across ALL Markets!
    public Map<ITradeable, Double> getIncrement(); 
    public void setIncrement(Map<ITradeable, Double> increment);
    public Double getFlatIncrement();
    public void setFlatIncrement(Double increment);    
    
    // Allocation rule
    public Map<Integer,List<ITradeable>> getAllocation();
    public void setAllocation(Map<Integer,List<ITradeable>> allocation);
    
    // Payment rule
    public void setPayments(List<AccountUpdate> payment);
    public List<AccountUpdate> getPayments();
    
    // orders (this is from the payment rule)
    // delete this !!! just use set Payments
    public void clearOrders();
    
    // Query rule
    public TradeRequestMessage getTRequest(); 
    public void setTRequest(TradeRequestMessage t);
    
    // Activity rule
    public boolean getAcceptable();
    public void setAcceptable(boolean b);
    
    public Map<ITradeable,Double> getReserve();
    public void setReserve(Map<ITradeable,Double> o); 
    
    public Map<ITradeable, List<Integer>> getAltAlloc();
    public void setAltAlloc(Map<ITradeable, List<Integer>> o);
    
    // IR policy 
    public Map<Integer, List<IInformationMessage>> getReport();
    public void setReport(Map<Integer,List<IInformationMessage>> gameReport);

    // Termination condition
    public long getTime();
   
    public void tick();
    public int getTicks();  
   
    public boolean isOpen();
    public void close(); 
    
    public boolean getOver();
    public void setOver(boolean b);
    
}
