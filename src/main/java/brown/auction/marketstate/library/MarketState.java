package brown.auction.marketstate.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.prevstate.library.PrevStateInfo;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.accounting.library.Order;
import brown.platform.messages.library.GameReportMessage;
import brown.platform.messages.library.TradeMessage;
import brown.platform.messages.library.TradeRequestMessage;
import brown.platform.twosided.IOrderBook;
import brown.platform.twosided.OrderBook;

/**
 * Standard MarketState stores the internal information of a 
 * market.
 * @author acoggins
 *
 */
public class MarketState implements IMarketState {

  private final Integer ID;  
  private final List<ITradeable> TRADEABLES;
  private List<TradeMessage> bids;
  private boolean isOpen; 
  private int ticks;  
  private long time;
  
  // grouping stuff
  private List<List<Integer>> groups;
  
  //allocation + payment rule
  private Map<Integer, List<ITradeable>> allocation;   
  private List<Order> payments; 
  //query rule
  private TradeRequestMessage tRequest;
  private Map<ITradeable, Double> increment;
  private double flatIncrement;
  //activity rules
  private Boolean isAcceptable; 
  private Map<ITradeable, Double> reserve;
  private Map<ITradeable, List<Integer>> altAlloc;
  //IRPolicy
  private Map<Integer,List<GameReportMessage>> gameReports; 
  //termination condition
  private Boolean terminated; 
  
  // carry over information?
  private PrevStateInfo prevState;
  private PrevStateInfo summaryState;
  
  // Orderbook for two sided markets
  private IOrderBook orderbook;
  
  public MarketState(Integer ID, List<ITradeable> allGoods, PrevStateInfo prevState) {
    this.ID = ID; 
    this.TRADEABLES = allGoods; 
    this.bids = new LinkedList<TradeMessage>();
    this.increment = new HashMap<ITradeable, Double>();
    this.ticks = 0; 
    this.terminated = false;
    this.allocation = new HashMap<Integer, List<ITradeable>>();
    this.altAlloc = new HashMap<ITradeable, List<Integer>>();
    this.payments = new LinkedList<Order>();
    this.prevState = prevState;
    this.groups = new LinkedList<List<Integer>>();
    this.orderbook = new OrderBook();
    this.time = System.currentTimeMillis();
    this.isOpen = true; 
    this.reserve = new HashMap<ITradeable, Double>();
    for (ITradeable t : this.TRADEABLES){
      this.reserve.put(t,0.);
      this.altAlloc.put(t,  new LinkedList<Integer>());
    }
  }
  
  @Override
  public Integer getID() {
    return this.ID; 
  }

  @Override
  public List<ITradeable> getTradeables() {
    return this.TRADEABLES; 
  }
  
  @Override
  public void tick() {
    this.ticks++;
  }

  @Override
  public int getTicks() {
    return this.ticks; 
  }
  
  @Override
  public long getTime() {
    return this.time;
  }

  @Override
  public void addBid(TradeMessage bid) {
    bids.add(bid);
  }

  @Override
  public void clearBids() {
   this.bids.clear(); 
  }
  
  @Override
  public void close() { 
    this.isOpen = false; 
  }
  
  @Override
  public boolean isOpen() {
    return this.isOpen; 
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
  public Map<ITradeable, Double> getIncrement() {
    return this.increment;
  }

  @Override
  public void setIncrement(Map<ITradeable, Double> increment) {
   for (ITradeable t : this.TRADEABLES) {
     if (!increment.containsKey(t)) {
       increment.put(t, 0.0); 
     }
   }
   this.increment = increment;
  }
  
  public Double getFlatIncrement(){
    return this.flatIncrement;
  }
  
  public void setFlatIncrement(Double increment){
    this.flatIncrement = increment;
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
  public Map<ITradeable,Double> getReserve() {
    return this.reserve;
  }

  @Override
  public void setReserve(Map<ITradeable,Double> reserve) {
     this.reserve = reserve;
  }
  
  @Override 
  public Map<ITradeable, List<Integer>> getAltAlloc() { 
    return this.altAlloc;
  }
  
  @Override
  public void setAltAlloc(Map<ITradeable, List<Integer>> altAlloc){
    this.altAlloc = altAlloc;
  }

  @Override
  public boolean getOver() {
    return this.terminated; 
  }

  @Override
  public void setOver(boolean over) {
    this.terminated = over; 
  }
  
  @Override
  public PrevStateInfo getPrevState() {
    return this.prevState;
  }

  @Override
  public PrevStateInfo getSummaryState() {
    return this.summaryState;
  }
  
  @Override
  public void setSummaryState(PrevStateInfo summary){
    this.summaryState = summary;
  }

  @Override
  public Map<Integer,List<GameReportMessage>>  getReport() {
    return this.gameReports; 
  }

  @Override
  public void setReport(Map<Integer,List<GameReportMessage>> gameReport) {
    this.gameReports = gameReport;
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

  @Override
  public List<List<Integer>> getGroups() {
    return this.groups;
  }

  @Override
  public void setGroups(List<List<Integer>> groups) {
    this.groups = groups;    
  }

  @Override
  public IOrderBook getOrderBook() {
    return this.orderbook;
  }
  
  @Override
  public void setOrderBook(IOrderBook book){
    this.orderbook = book;
  }


}
