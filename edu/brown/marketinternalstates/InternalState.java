package brown.marketinternalstates; 

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.assets.accounting.Order;
import brown.bundles.BidBundle;
import brown.bundles.BundleType;
import brown.bundles.MarketState;
import brown.bundles.SimpleBidBundle;
import brown.channels.MechanismType;
import brown.messages.auctions.Bid;
import brown.messages.auctions.BidRequest;
import brown.messages.markets.GameReport;
import brown.messages.markets.TradeRequest;
import brown.rules.paymentrules.PaymentType;
import brown.tradeables.Asset;
import brown.valuable.library.Tradeable;


//hopefully an integrating center for information that pertains to markets and rules.
public class InternalState implements MarketInternalState {
  
  //things not directly associated with any rule. There may be some overlap.
  private final double INCREMENT = 20.0;
  private final Integer ID;
  private final List<Bid> BIDS;
  private final Set<Asset> TRADEABLES;
  
  private BidBundle lastAlloc;
  private List<Order> lastPayments;
  private int ticks;
  private BidBundle bundleReserve;
  private boolean maximizing;
  
//  //current and temporary things that could be inputs to arguments.
//  public Ledger ledge; 
//  public Map<Integer, Set<Asset>> allocations; 
//  public Set<Bid> bids; 
//  public Bid aBid;
//  public Integer anID;
  
  //allocation rule things.
  private long time; 
  private BidBundle alloc;
  private Map<Integer, Set<Asset>> allocs;
  private BidRequest request;
  private boolean isPrivate; 
  private boolean isOver; 
  private BundleType bType; 
  private Set<Bid> reserve; 
  private boolean valid; 
  private MechanismType mType; 
  private GameReport report;
  
  //payment rule things
  private List<Order> payments; 
  private Map<BidBundle, Set<Asset>> oPayments; 
  private PaymentType pType; 
  private BidBundle reserveBundle; 
  private boolean permitShort; 
  
  //query rule things.
  private TradeRequest tRequest; 
  
  //activity rule things. 
  private boolean isAcceptable; 
  
  //IRPolicy things
  //come back to this.
  
  //tcondition things. 
  private boolean terminated; 
  
  
  
  public InternalState(Integer ID, Set<Asset> tradeables) {
    this.BIDS = new LinkedList<Bid>();
    this.lastAlloc = null;
    this.lastPayments = null;
    this.TRADEABLES = tradeables;
    this.ID = ID;
    this.ticks = 0;
    Map<Tradeable, MarketState> reserve = new HashMap<Tradeable, MarketState>();
    for (Asset t : this.TRADEABLES) {
      reserve.put(t.getType(), new MarketState(null,0));
    }
    this.bundleReserve = new SimpleBidBundle(reserve);
    this.maximizing = false;
  }
  
  //methods not directly associated with a rule. Look for redundancies here.
  public void addBid(Bid bid) {
    this.ticks = 0;
    this.BIDS.add(bid);
  }
  

  public List<Bid> getBids() {
    return this.BIDS;
  }
  
  public Set<Asset> getTradeables() {
    return this.TRADEABLES;
  }
  
  public Integer getID() {
    return this.ID;
  }
  
  public void setPayments_two(List<Order> payments) {
    this.lastPayments = payments;
  }
  
  public List<Order> getPayments_two() {
    return this.lastPayments;
  }
  
  public void tick(long time) {
    this.ticks++;   
  }
  
  public int getTicks() {
    return this.ticks;
  } 
  
  public void setReserve(BidBundle o) {
    this.bundleReserve = o;
  }
  
  public BidBundle getbundleReserve() {
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
    for (Tradeable type : bundle.getDemandSet()) {
      MarketState state = bundle.getBid(type);
      if (state != null && state.AGENTID != null) {
        elig+=1;
      }
    }
    return elig;
  }
  
  
  
  //getters for all the allocation rules.
  public long getTime() {
    return this.time; 
  }
  
  public BidBundle getAllocation() {
    return this.alloc; 
  }
  
  public Map<Integer, Set<Asset>> getAllocs() {
    return this.allocs;
  }
  
  public BidRequest getRequest() { 
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
  
  public Set<Bid> getReserve() {
    return this.reserve; 
  }
  
  public boolean getValid() {
    return this.valid;
  }
  
  public MechanismType getMType() {
    return this.mType; 
  }
  
  public GameReport getReport() {
    return this.report; 
  }
  
  //setters for allocation rule.
  public void setTime(long t) {
    this.time = t; 
  }
  
  public void setAllocation(BidBundle alloc) {
    this.alloc = alloc; 
  }
 
  public void setAllocs(Map<Integer, Set<Asset>> allocs) {
    this.allocs = allocs;
  }
  
  public void setRequest(BidRequest request) { 
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
  
  public void setReserve(Set<Bid> r) {
     this.reserve = r; 
  }
  
  public void setValid(boolean v) {
     this.valid = v;
  }
  
  public void setMType(MechanismType m) {
     this.mType = m; 
  }
  
  public void setReport(GameReport g) {
     this.report = g; 
  }
  
  //getters for payment rules. 
  
  public List<Order> getPayments() {
    return this.payments; 
  }
  
  public Map<BidBundle, Set<Asset>> getOPayments() {
    return this.oPayments;
  }
  
  public PaymentType getPaymentType() {
    return this.pType; 
  }
  
  public BidBundle getReserveBundle() {
    return this.reserveBundle;
  }
  
  public boolean permitShort() { 
    return this.permitShort;
  }
  
  //setter for payment rules.
  
  
  public void setPayments(List<Order> orders) {
    this.payments = orders; 
  }
  
  public void setOPayments(Map<BidBundle, Set<Asset>> m) {
     this.oPayments = m;
  }
  
  public void setPaymentType(PaymentType p) {
     this.pType = p; 
  }
  
  public void setReserveBundle(BidBundle b) {
     this.reserveBundle = b;
  }
  
  public void setShort(boolean b) { 
    this.permitShort = b;
  }
  
  //for Query Rules
  
  public TradeRequest getTRequest() {
      return this.tRequest; 
  }
  
  public void setTRequest(TradeRequest t) {
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
  public boolean getTOver() {
    return this.terminated; 
  }
  
  public void setTOver(boolean b) {
    this.terminated = b; 
  }

}