package brown.market.marketstate;

import java.util.List;
import java.util.Set;

import abrown.misc.Allocation;
import brown.accounting.bidbundle.IBidBundle;
import brown.accounting.bidbundle.library.BundleType;
import brown.channels.MechanismType;
import brown.market.marketstate.library.MarketState;
import brown.market.marketstate.library.Order;
import brown.messages.library.TradeMessage;
import brown.messages.library.BidRequestMessage;
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
    
    public void clear();
    
    public List<TradeMessage> getBids(); 
    
    public Integer getID(); 
    
    public void setLastPayments(List<Order> payments); 
    
    public List<Order> getLastPayments(); 
    
    public void tick(long time); 
    
    public int getTicks();  
    
    public void setReserve(IBidBundle o); 
    
    public IBidBundle getbundleReserve(); 
    
    public void clearBids(); 
    
    public double getIncrement(); 
    
    public void setMaximizingRevenue(boolean b); 
    
    public boolean isMaximizingRevenue(); 
  
    public int getEligibility(); 
    
    
    
    //getters for all the allocation rules.
    
    public Allocation getAllocation();  
    
    //setters for allocation rule.
    
    public void setAllocation(Allocation alloc);
    
    //getters for payment rules. 
    
    public List<Order> getPayments();
    
    public PaymentType getPaymentType();
    
    public IBidBundle getReserveBundle();
    
    public boolean permitShort(); 
    
    //setter for payment rules.
    
    public void setPayments(List<Order> orders); 
    
    public void setPaymentType(PaymentType p); 
    
    public void setReserveBundle(IBidBundle b); 
    
    public void setShort(boolean b);
    
    //for Query Rules
    
    public TradeRequestMessage getTRequest(); 
    
    public void setTRequest(TradeRequestMessage t);
    
    public boolean getAcceptable();
    
    public void setAcceptable(boolean b);
    
    //tcondition things
    public boolean getInnerOver();
    
    public void setInnerOver(boolean b); 
    
    public boolean getOuterOver();
    
    public void setOuterOver(boolean b); 
    
    public void incrementOuter();
    
    public Integer getOuterRuns();

}
