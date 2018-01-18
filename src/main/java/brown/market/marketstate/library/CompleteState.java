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
  private List<Order> orders; 
  private int ticks;  
  
  //allocation + payent rule
  private MarketState marketState;
  //query rule
  private TradeRequestMessage tRequest;
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
    this.BIDS = new LinkedList<TradeMessage>();
    this.orders = new LinkedList<Order>();
    this.marketState = new MarketState();
    this.ticks = 0; 
    this.innerRuns = 0; 
    this.outerRuns = 0; 
    this.innerTerminated = false; 
    this.outerTerminated = false; 
  }
  
  @Override
  public Integer getID() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MarketState getMarketState() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setMarketState(MarketState m) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void tick(long time) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public int getTicks() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void addBid(TradeMessage bid) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void clearBids() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public List<TradeMessage> getBids() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Order> getOrders() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void constructOrders() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void clearOrders() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public TradeRequestMessage getTRequest() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setTRequest(TradeRequestMessage t) {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public Double getIncrement() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setIncrement(Double increment) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean getAcceptable() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setAcceptable(boolean b) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public IBidBundle getReserve() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setReserve(IBidBundle o) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean getInnerOver() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setInnerOver(boolean b) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean getOuterOver() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setOuterOver(boolean b) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void incrementOuter() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Integer getOuterRuns() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public GameReportMessage getReport() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setReport(GameReportMessage gameReport) {
    // TODO Auto-generated method stub
    
  }
  
}