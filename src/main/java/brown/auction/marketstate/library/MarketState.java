package brown.auction.marketstate.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.library.TradeRequestMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.library.AccountUpdate;
import brown.platform.item.ICart;

/**
 * Standard MarketState stores the internal information of a market.
 * 
 * @author acoggins
 */
public class MarketState implements IMarketState {
  
  private int ticks;  
  private long time;
  
  // Allocation rule
  private Map<Integer, List<ICart>> allocation;
  
  // Payment rule
  private List<IAccountUpdate> payments;
  
  // Query rule
  private TradeRequestMessage tRequest;
  
  private double flatIncrement;
  
  // Activity rule
  private Boolean isAcceptable; 

  
  // IR policy
  private Map<Integer, List<IInformationMessage>> gameReports;
  
  // Termination condition
  private boolean isOpen;
  
  public MarketState() {
    this.allocation = new HashMap<Integer, List<ICart>>();
    this.payments = new LinkedList<IAccountUpdate>();
    this.time = System.currentTimeMillis();
    this.isOpen = true; 
//      this.reserve.put(t,0.);
//      this.altAlloc.put(t,  new LinkedList<Integer>());
//    }
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
  public void close() { 
    this.isOpen = false; 
  }
  
  @Override
  public boolean isOpen() {
    return this.isOpen; 
  }

  // TODO: clearAllocation()
  // Maybe rename this method clearPayments:
  @Override
  public void clearOrders() {
    this.setPayments(new LinkedList<IAccountUpdate>());
  }

  @Override
  public TradeRequestMessage getTRequest() {
    return this.tRequest;
  }

  @Override
  public void setTRequest(TradeRequestMessage t) {
    this.tRequest = t;
  }
  
  
  public Double getFlatIncrement() {
    return this.flatIncrement;
  }
  
  public void setFlatIncrement(Double increment) {
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
  public Map<Integer, List<IInformationMessage>>  getReport() {
    return this.gameReports; 
  }

  @Override
  public void setReport(Map<Integer, List<IInformationMessage>> gameReport) {
    this.gameReports = gameReport;
  }

  @Override
  public Map<Integer, List<ICart>> getAllocation() {
    return this.allocation;
  }

  @Override
  public List<IAccountUpdate> getPayments() {
    return this.payments;
  }

  @Override
  public void setAllocation(Map<Integer, List<ICart>> allocation) {
    this.allocation = allocation;
  }

  @Override
  public void setPayments(List<IAccountUpdate> payments) {
    this.payments = payments;
  }

}
