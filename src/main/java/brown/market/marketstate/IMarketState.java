package brown.market.marketstate;

import java.util.List;
import java.util.Set;

import brown.accounting.BundleType;
import brown.accounting.Order;
import brown.accounting.bidbundle.AbsBidBundle;
import brown.accounting.bidbundle.Allocation;
import brown.channels.MechanismType;
import brown.messages.library.Bid;
import brown.messages.library.BidRequest;
import brown.messages.library.GameReport;
import brown.messages.library.TradeRequest;
import brown.todeprecate.PaymentType;

/**
 * stores the internal state of a market as 
 * bidding is occurring.
 * @author acoggins
 *
 */
public interface IMarketState {

    //methods not directly associated with a rule. Look for redundancies here.
    public void addBid(Bid bid);
    
    public void clear();
    
    public List<Bid> getBids(); 
    
    public Integer getID(); 
    
    public void setPayments_two(List<Order> payments); 
    
    public List<Order> getPayments_two(); 
    
    public void tick(long time); 
    
    public int getTicks();  
    
    public void setReserve(AbsBidBundle o); 
    
    public AbsBidBundle getbundleReserve(); 
    
    public void clearBids(); 
    
    public double getIncrement(); 
    
    public void setMaximizingRevenue(boolean b); 
    
    public boolean isMaximizingRevenue(); 
  
    public int getEligibility(); 
    
    
    
    //getters for all the allocation rules.
    public long getTime(); 
    
    public Allocation getAllocation(); 
    
    public BidRequest getRequest(); 
    
    public boolean getPrivate();
    
    public boolean getOver(); 
    
    public BundleType getBundleType(); 
    
    public Set<Bid> getReserve(); 
    
    public boolean getValid();
    
    public MechanismType getMType();
    
    public GameReport getReport(); 
    
    //setters for allocation rule.
    public void setTime(long t);
    
    public void setAllocation(Allocation alloc);

    public void setRequest(BidRequest request);
    
    public void setPrivate(boolean p); 
    
    public void setOver(boolean o); 
    
    public void setBundleType(BundleType b); 
    
    public void setReserve(Set<Bid> r); 
    
    public void setValid(boolean v);
    
    public void setMType(MechanismType m);
    
    public void setReport(GameReport g);
    
    //getters for payment rules. 
    
    public List<Order> getPayments();
    
    public PaymentType getPaymentType();
    
    public AbsBidBundle getReserveBundle();
    
    public boolean permitShort(); 
    
    //setter for payment rules.
    
    public void setPayments(List<Order> orders); 
    
    public void setPaymentType(PaymentType p); 
    
    public void setReserveBundle(AbsBidBundle b); 
    
    public void setShort(boolean b);
    
    //for Query Rules
    
    public TradeRequest getTRequest(); 
    
    public void setTRequest(TradeRequest t);
    
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
