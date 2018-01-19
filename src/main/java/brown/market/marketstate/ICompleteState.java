package brown.market.marketstate;

import java.util.List;
import java.util.Set;

import brown.accounting.bidbundle.IBidBundle;
import brown.market.marketstate.library.MarketState;
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
public interface ICompleteState {

    public Integer getID(); 
  
    public Set<ITradeable> getTradeables();
    
    // Orders contained here
    public MarketState getMarketState();
    
    public void setMarketState(MarketState m);
    
    public void tick(long time); 
    
    public int getTicks();  

    //bids
    
    public void addBid(TradeMessage bid);
    
    public void clearBids();
    
    public List<TradeMessage> getBids(); 
    
    // orders (this is from the payment rule)
    
    public void clearOrders();
    
    //allocation and payment rule interact through marketstate
    
    //for Query Rules
    
    public TradeRequestMessage getTRequest(); 
    
    public void setTRequest(TradeRequestMessage t);
    
    public Double getIncrement(); 
    
    public void setIncrement(Double increment);
    
    
    //activity rules
    
    public boolean getAcceptable();
    
    public void setAcceptable(boolean b);
    
    public IBidBundle getReserve();
    
    public void setReserve(IBidBundle o); 
    
    //tcondition things
    public boolean getInnerOver();
    
    public void setInnerOver(boolean b); 
    
    public boolean getOuterOver();
    
    public void setOuterOver(boolean b); 
    
    public void incrementOuter();
    
    public Integer getOuterRuns();
    
    //IR policy 
    public GameReportMessage getReport();
    
    public void setReport(GameReportMessage gameReport);
    
    //TODO: market update. 

}
