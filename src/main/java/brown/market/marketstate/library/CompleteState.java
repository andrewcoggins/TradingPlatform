package brown.market.marketstate.library;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.accounting.bidbundle.IBidBundle;
import brown.market.marketstate.ICompleteState;
import brown.messages.library.GameReportMessage;
import brown.messages.library.TradeMessage;
import brown.messages.library.TradeRequestMessage;
import brown.tradeable.ITradeable;

public class CompleteState implements ICompleteState {

  private final Integer ID;  
  private final Set<ITradeable> TRADEABLES;
  private List<TradeMessage> BIDS;
  private int ticks;  
  
  //allocation + payment rule
  private Allocation allocation; 
  private Payment payment; 
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
  
  
  public CompleteState(Integer ID, Set<ITradeable> tradeables) {
    this.ID = ID; 
    this.TRADEABLES = tradeables; 
    this.BIDS = new LinkedList<TradeMessage>();
    this.outerTerminated = false; 
    this.increment = 0.0;
    this.ticks = 0; 
    this.outerRuns = 0; 
    this.innerTerminated = false; 
    this.allocation = new Allocation();
    this.payment = new Payment();
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
    BIDS.add(bid);
  }

  @Override
  public void clearBids() {
   this.BIDS = new LinkedList<TradeMessage>();
  }

  @Override
  public List<TradeMessage> getBids() {
    return this.BIDS; 
  }

  @Override
  public void clearOrders() {
    this.setPayments(new Payment());
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
    this.BIDS = new LinkedList<TradeMessage>();
    this.allocation = new Allocation();
    this.payment = new Payment();
    this.increment = 0.0;
    this.ticks = 0; 
    this.innerTerminated = false;   
    }

  @Override
  public Allocation getAllocation() {
    return this.allocation;
  }

  @Override
  public Payment getPayments() {
    return this.payment;
  }

  @Override
  public void setAllocation(Allocation allocation) {
    this.allocation = allocation;
  }

  @Override
  public void setPayments(Payment payment) {
    this.payment = payment;
  }
}
