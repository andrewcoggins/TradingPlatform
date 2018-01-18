package brown.market.marketstate;

import java.util.List;
import java.util.Set;

import abrown.misc.library.Allocation;
import brown.accounting.bidbundle.IBidBundle;
import brown.accounting.bidbundle.library.BundleType;
import brown.channels.MechanismType;
import brown.market.marketstate.library.MarketState;
import brown.market.marketstate.library.Order;
import brown.messages.library.TradeMessage;
import brown.messages.library.BidRequestMessage;
import brown.messages.library.GameReport;
import brown.messages.library.GameReportMessage;
import brown.messages.library.TradeRequestMessage;
import brown.todeprecate.PaymentType;

/**
 * stores the internal state of a market as 
 * bidding is occurring.
 * 
 * @author acoggins
 */
public interface ICompleteState {

    public MarketState getMarketState();
    
    //methods not directly associated with a rule. Look for redundancies here.
    public void addBid(TradeMessage bid);
    
    public void clearBids();
    
    public void clearOrders();
    
    public List<TradeMessage> getBids(); 
    
    public Integer getID(); 
    
    public void tick(long time); 
    
    public int getTicks();  
    
    public void setReserve(IBidBundle o); 
    
    // allocation rule.
    
    public Allocation getAllocation();  
    
    public void setAllocation(Allocation alloc);
    
    // payment rule. 
    
    public List<Order> getPayments();
    
    public void setPayments(List<Order> orders); 
    
    //for Query Rules
    
    public TradeRequestMessage getTRequest(); 
    
    public void setTRequest(TradeRequestMessage t);
    
    
    //activity rules
    
    public boolean getAcceptable();
    
    public void setAcceptable(boolean b);
    
    public IBidBundle getReserve();
    
    public void setReserve();
    
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

}
