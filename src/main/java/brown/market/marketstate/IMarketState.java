package brown.market.marketstate;

import java.util.List;
import java.util.Set;

import brown.accounting.BundleType;
import brown.accounting.Order;
import brown.accounting.bidbundle.IBidBundle;
import brown.accounting.bidbundle.library.Allocation;
import brown.channels.MechanismType;
import brown.messages.library.BidMessage;
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
public interface IMarketState {

    //methods not directly associated with a rule. Look for redundancies here.
    public void addBid(BidMessage bid);
    
    public void clear();
    
    public List<BidMessage> getBids(); 
    
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
    public long getTime(); 
    
    public IBidBundle getAllocation(); 
    
    public BidRequestMessage getRequest(); 
    
    public boolean getPrivate();
    
    public boolean getOver(); 
    
    public BundleType getBundleType(); 
    
    public Set<BidMessage> getReserve(); 
    
    public boolean getValid();
    
    public MechanismType getMType();
    
    public GameReportMessage getReport(); 
    
    //setters for allocation rule.
    public void setTime(long t);
    
    public void setAllocation(IBidBundle alloc);

    public void setRequest(BidRequestMessage request);
    
    public void setPrivate(boolean p); 
    
    public void setOver(boolean o); 
    
    public void setBundleType(BundleType b); 
    
    public void setReserve(Set<BidMessage> r); 
    
    public void setValid(boolean v);
    
    public void setMType(MechanismType m);
    
    public void setReport(GameReportMessage g);
    
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
