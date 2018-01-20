package brown.market.marketstate.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.bid.bidbundle.IBidBundle;
import brown.market.marketstate.IMarketState;
import brown.messages.library.GameReportMessage;
import brown.messages.library.TradeMessage;
import brown.messages.library.TradeRequestMessage;
import brown.tradeable.ITradeable;

public class MarketState implements IMarketState {

  private final Integer ID;  
  private final Set<ITradeable> TRADEABLES;
  private List<TradeMessage> bids;
  private int ticks;  
  
  //allocation + payment rule
  private Map<Integer, List<ITradeable>> allocation;   
  private List<Order> payments; 
  //query rule
  private TradeRequestMessage tRequest;
  private Double increment;
  //activity rules
  private Boolean isAcceptable; 
  private IBidBundle reserve;
  //IRPolicy
  private GameReportMessage gameReport; 
  //termination condition
  private Boolean innerTerminated; 
  private Boolean outerTerminated; 
  private Integer outerRuns; 
  
  
  public MarketState(Integer ID, Set<ITradeable> tradeables) {
    this.ID = ID; 
    this.TRADEABLES = tradeables; 
    this.bids = new LinkedList<TradeMessage>();
    this.outerTerminated = false; 
    this.increment = 0.0;
    this.ticks = 0; 
    this.outerRuns = 0; 
    this.innerTerminated = false; 
    this.allocation = new HashMap<Integer, List<ITradeable>>();
    this.payments = new LinkedList<Order>();
  }
  
  @Override
  public Integer getID() {
    return this.ID; 
  }

  @Override
  public Set<ITradeable> getTradeables() {
    return this.TRADEABLES; 
  }
  
  @Override
  public void tick(long time) {
    this.ticks++;
  }

  @Override
  public int getTicks() {
    return this.ticks; 
  }

  @Override
  public void addBid(TradeMessage bid) {
    bids.add(bid);
  }

  @Override
  public void clearBids() {
   this.bids = new LinkedList<TradeMessage>();
  }

  @Override
  public List<TradeMessage> getBids() {
    return this.bids; 
  }

  @Override
  public void clearOrders() {
    this.setPayments(new LinkedList<Order>());
  }

  @Override
  public TradeRequestMessage getTRequest() {
    return this.tRequest;
  }

  @Override
  public void setTRequest(TradeRequestMessage t) {
    this.tRequest = t;
  }
  
  @Override
  public Double getIncrement() {
    return this.increment;
  }

  @Override
  public void setIncrement(Double increment) {
   this.increment = increment;
  }

  @Override
  public boolean getAcceptable() {
    return isAcceptable; 
  }

  @Override
  public void setAcceptable(boolean acceptable) {
    this.isAcceptable = acceptable; 
  }

  @Override
  public IBidBundle getReserve() {
    return this.reserve;
  }

  @Override
  public void setReserve(IBidBundle reserve) {
     this.reserve = reserve;
  }

  @Override
  public boolean getInnerOver() {
    return this.innerTerminated; 
  }

  @Override
  public void setInnerOver(boolean over) {
    this.innerTerminated = over; 
  }

  @Override
  public boolean getOuterOver() {
    return this.outerTerminated; 
  }

  @Override
  public void setOuterOver(boolean over) {
    this.outerTerminated = over;
  }

  @Override
  public void incrementOuter() {
    this.outerRuns++;
  }

  @Override
  public Integer getOuterRuns() {
    return this.outerRuns;
  }

  @Override
  public GameReportMessage getReport() {
    return this.gameReport; 
  }

  @Override
  public void setReport(GameReportMessage gameReport) {
    this.gameReport = gameReport;
  }

  // resets everything EXCEPT outer terminated condition and outer runs
  @Override
  public void reset() {
    this.bids = new LinkedList<TradeMessage>();
    this.allocation = new HashMap<Integer, List<ITradeable>>();
    this.payments = new LinkedList<Order>();
    this.increment = 0.0;
    this.ticks = 0; 
    this.innerTerminated = false;   
    }

  @Override
  public Map<Integer,List<ITradeable>> getAllocation() {
    return this.allocation;
  }

  @Override
  public List<Order> getPayments() {
    return this.payments;
  }

  @Override
  public void setAllocation(Map<Integer,List<ITradeable>> allocation) {
    this.allocation = allocation;
  }

  @Override
  public void setPayments(List<Order> payments) {
    this.payments = payments;
  }
}
