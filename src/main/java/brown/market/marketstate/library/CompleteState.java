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
  private List<TradeMessage> bids;
  private int ticks;  
  
  //allocation + payment rule
  private MarketState marketState;
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
  private Integer innerRuns;
  private Integer outerRuns; 
  
  
  public CompleteState(Integer ID, Set<ITradeable> tradeables) {
    this.ID = ID; 
    this.TRADEABLES = tradeables; 
    this.bids = new LinkedList<TradeMessage>();
    this.marketState = new MarketState();
    this.outerTerminated = false; 
    this.increment = 0.0;
    this.ticks = 0; 
    this.innerRuns = 0; 
    this.outerRuns = 0; 
    this.innerTerminated = false; 
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
  public MarketState getMarketState() {
     return this.marketState; 
  }

  @Override
  public void setMarketState(MarketState m) {
    this.marketState = m; 
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
    this.marketState.setPayments(new Payment());
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
}
