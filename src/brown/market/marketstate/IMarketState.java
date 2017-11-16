package brown.market.marketstate;

import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.accounting.BidBundle;
import brown.accounting.BundleType;
import brown.accounting.Order;
import brown.channels.MechanismType;
import brown.messages.library.Bid;
import brown.messages.library.BidRequest;
import brown.messages.library.GameReport;
import brown.messages.library.TradeRequest;
import brown.todeprecate.Asset;
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
    
  
    public List<Bid> getBids(); 
    
    public Set<Asset> getTradeables(); 
    
    public Integer getID(); 
    
    public void setPayments_two(List<Order> payments); 
    
    public List<Order> getPayments_two(); 
    
    public void tick(long time); 
    
    public int getTicks();  
    
    public void setReserve(BidBundle o); 
    
    public BidBundle getbundleReserve(); 
    
    public void clearBids(); 
    
    public double getIncrement(); 
    
    public void setMaximizingRevenue(boolean b); 
    
    public boolean isMaximizingRevenue(); 
  
    public int getEligibility(); 
    
    
    
    //getters for all the allocation rules.
    public long getTime(); 
    
    public BidBundle getAllocation(); 
    
    public Map<Integer, Set<Asset>> getAllocs(); 
    
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
    
    public void setAllocation(BidBundle alloc);
   
    public void setAllocs(Map<Integer, Set<Asset>> allocs);
    
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

    public Map<BidBundle, Set<Asset>> getOPayments(); 
    
    public PaymentType getPaymentType();
    
    public BidBundle getReserveBundle();
    
    public boolean permitShort(); 
    
    //setter for payment rules.
    
    public void setPayments(List<Order> orders); 
    
    public void setOPayments(Map<BidBundle, Set<Asset>> m); 
    
    public void setPaymentType(PaymentType p); 
    
    public void setReserveBundle(BidBundle b); 
    
    public void setShort(boolean b);
    
    //for Query Rules
    
    public TradeRequest getTRequest(); 
    
    public void setTRequest(TradeRequest t);
    
    public boolean getAcceptable();
    
    public void setAcceptable(boolean b);
    
    //tcondition things
    public boolean getTOver();
    
    public void setTOver(boolean b); 

}
