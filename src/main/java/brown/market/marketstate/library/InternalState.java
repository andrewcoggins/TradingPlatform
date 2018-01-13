package brown.market.marketstate.library; 

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.accounting.BundleType;
import brown.accounting.MarketState;
import brown.accounting.Order;
import brown.accounting.bidbundle.AbsBidBundle;
import brown.accounting.bidbundle.IBidBundle;
import brown.accounting.bidbundle.library.Allocation;
import brown.accounting.bidbundle.library.SimpleBidBundle;
import brown.channels.MechanismType;
import brown.market.marketstate.IMarketState;
import brown.messages.library.BidMessage;
import brown.messages.library.BidRequestMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.TradeRequestMessage;
import brown.todeprecate.PaymentType;
import brown.tradeable.library.Tradeable;


//hopefully an integrating center for information that pertains to markets and rules.
public class InternalState implements IMarketState {
  
  //things not directly associated with any rule. There may be some overlap.
  private final double INCREMENT = 20.0;
  private final Integer ID;
  private final List<BidMessage> BIDS;
  private final Set<Tradeable> TRADEABLES;
  
  private List<Order> lastPayments;
  private int ticks;
  private IBidBundle bundleReserve;
  private boolean maximizing;
  
  
  //allocation rule things.
  private long time; 
  private IBidBundle alloc;
  private BidRequestMessage request;
  private boolean isPrivate; 
  private boolean isOver; 
  private BundleType bType; 
  private Set<BidMessage> reserve; 
  private boolean valid; 
  private MechanismType mType; 
  private GameReportMessage report;
  
  //payment rule things
  private List<Order> payments; 
  private PaymentType pType; 
  private IBidBundle reserveBundle; 
  private boolean permitShort; 
  
  //query rule things.
  private TradeRequestMessage tRequest; 
  
  //activity rule things. 
  private boolean isAcceptable; 
  
  //IRPolicy things
  //come back to this.
  
  //tcondition things. 
  private boolean innerTerminated; 
  private boolean outerTerminated;
  private Integer innerRuns; 
  private Integer outerRuns; 
  
  
  
  public InternalState(Integer ID, Set<Tradeable> tradeables) {
    this.BIDS = new LinkedList<BidMessage>();
    this.lastPayments = null;
    this.TRADEABLES = tradeables;
    this.ID = ID;
    this.ticks = 0;
    Map<Tradeable, MarketState> reserve = new HashMap<Tradeable, MarketState>();
    if (this.TRADEABLES != null) {
      for (Tradeable t : this.TRADEABLES) {
        reserve.put(t, new MarketState(null,0));
      }
    }
    this.bundleReserve = new SimpleBidBundle(reserve);
    this.maximizing = false;
  }
  
  //methods not directly associated with a rule. Look for redundancies here.
  public void addBid(BidMessage bid) {
    this.ticks = 0;
    this.BIDS.add(bid);
  }
  
  /**
   * add on here as needed.
   */
  public void clear() {
    if (payments != null) {
      this.payments.clear();
    }
    this.BIDS.clear(); 
  }
  
  public List<BidMessage> getBids() {
    return this.BIDS;
  }
  
  public Set<Tradeable> getTradeables() {
    return this.TRADEABLES;
  }
  
  public Integer getID() {
    return this.ID;
  }
  
  public void setLastPayments(List<Order> payments) {
    this.lastPayments = payments;
  }
  
  public List<Order> getLastPayments() {
    return this.lastPayments;
  }
  
  public void tick(long time) {
    this.ticks++;   
  }
  
  public int getTicks() {
    return this.ticks;
  } 
  
  public void setReserve(IBidBundle o) {
    this.bundleReserve = o;
  }
  
  public IBidBundle getbundleReserve() {
    return this.bundleReserve;
  }
  
  public void clearBids() {
    this.BIDS.clear();
  }
  
  public double getIncrement() {
    return this.INCREMENT;
  }
  
  public void setMaximizingRevenue(boolean b) {
    this.maximizing = b;
  }
  
  public boolean isMaximizingRevenue() {
    return this.maximizing;
  }

  public int getEligibility() {
    int elig = 0;
    if (this.reserve == null) {
      return 0;
    }
    SimpleBidBundle bundle = (SimpleBidBundle) this.reserve;
//    for (Tradeable type : bundle.getDemandSet()) {
//      MarketState state = bundle.getBid(type);
//      if (state != null && state.AGENTID != null) {
//        elig+=1;
//      }
//    }
    return elig;
  }
  
  
  
  //getters for all the allocation rules.
  public long getTime() {
    return this.time; 
  }
  
  public IBidBundle getAllocation() {
    return this.alloc; 
  }
  
  public BidRequestMessage getRequest() { 
    return this.request;
  }
  
  public boolean getPrivate() {
    return this.isPrivate; 
  }
  
  public boolean getOver() {
    return this.isOver; 
  }
  
  public BundleType getBundleType() {
    return this.bType;
  }
  
  public Set<BidMessage> getReserve() {
    return this.reserve; 
  }
  
  public boolean getValid() {
    return this.valid;
  }
  
  public MechanismType getMType() {
    return this.mType; 
  }
  
  public GameReportMessage getReport() {
    return this.report; 
  }
  
  //setters for allocation rule.
  public void setTime(long t) {
    this.time = t; 
  }
  
  public void setAllocation(IBidBundle alloc) {
    this.alloc = alloc; 
  }
  
  public void setRequest(BidRequestMessage request) { 
     this.request = request;
  }
  
  public void setPrivate(boolean p) {
     this.isPrivate = p; 
  }
  
  public void setOver(boolean o) {
     this.isOver = o; 
  }
  
  public void setBundleType(BundleType b) {
     this.bType = b;
  }
  
  public void setReserve(Set<BidMessage> r) {
     this.reserve = r; 
  }
  
  public void setValid(boolean v) {
     this.valid = v;
  }
  
  public void setMType(MechanismType m) {
     this.mType = m; 
  }
  
  public void setReport(GameReportMessage g) {
     this.report = g; 
  }
  
  //getters for payment rules. 
  
  public List<Order> getPayments() {
    return this.payments; 
  }
  
  public PaymentType getPaymentType() {
    return this.pType; 
  }
  
  public IBidBundle getReserveBundle() {
    return this.reserveBundle;
  }
  
  public boolean permitShort() { 
    return this.permitShort;
  }
  
  //setter for payment rules.
  
  
  public void setPayments(List<Order> orders) {
    this.payments = orders; 
  }
  
  public void setPaymentType(PaymentType p) {
     this.pType = p; 
  }
  
  public void setReserveBundle(IBidBundle b) {
     this.reserveBundle = b;
  }
  
  public void setShort(boolean b) { 
    this.permitShort = b;
  }
  
  //for Query Rules
  
  public TradeRequestMessage getTRequest() {
      return this.tRequest; 
  }
  
  public void setTRequest(TradeRequestMessage t) {
    this.tRequest = t; 
}
  
  //for activity rules
  public boolean getAcceptable() {
    return this.isAcceptable;
  }
  
  public void setAcceptable(boolean b) {
    this.isAcceptable = b;
  }
  
  //tcondition things
  public boolean getInnerOver() {
    return this.innerTerminated; 
  }
  
  public void setInnerOver(boolean b) {
    this.innerTerminated = b; 
  }

  public boolean getOuterOver() { 
    return this.outerTerminated;
  }
  
  public void setOuterOver(boolean b) {
    this.outerTerminated = b;
  }
  
  public void incrementOuter() {
    if(this.outerRuns == null) {
      this.outerRuns = 1;
    } else {
      this.outerRuns++;
    }
  }
  
  public Integer getOuterRuns() {
    if(this.outerRuns == null) {
      return 0;
    }
    return this.outerRuns;
  }
}