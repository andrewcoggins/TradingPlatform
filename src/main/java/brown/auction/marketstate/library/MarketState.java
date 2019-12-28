package brown.auction.marketstate.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.ActionRequestMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.item.ICart;

/**
 * Standard MarketState stores the internal information of a market.
 * 
 * @author acoggins
 */
public class MarketState implements IMarketState {

  private int ticks;
  private long time;
  
  // TODO: work the trade history in this guy. 
  // the trade history needs to be in here for the current step. 
  
  // history
  private List<List<ITradeMessage>> tradeHistory;

  // Allocation rule

  private Map<Integer, List<ICart>> allocation;

  // Payment rule
  private List<IAccountUpdate> payments;

  // Query rule
  private ActionRequestMessage tRequest;

  // Activity rule
  private Boolean isAcceptable;
  // activity rule also deals with reserve prices. 
  private Map<String, Double> reserves; 

  // Termination condition
  private boolean isOpen;

  public MarketState() {
    this.allocation = new HashMap<Integer, List<ICart>>();
    this.payments = new LinkedList<IAccountUpdate>();
    this.time = System.currentTimeMillis();
    this.isOpen = true;
    this.tradeHistory = new LinkedList<List<ITradeMessage>>();
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
  public ActionRequestMessage getTRequest() {
    return this.tRequest;
  }

  @Override
  public void setTRequest(ActionRequestMessage t) {
    this.tRequest = t;
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

  @Override
  public List<List<ITradeMessage>> getTradeHistory() {
    return this.tradeHistory;
  }

  @Override
  public void addToTradeHistory(List<ITradeMessage> tradeMessages) {
    this.tradeHistory.add(tradeMessages);
  }

  @Override
  public Map<String, Double> getReserves() {
    return this.reserves; 
  }

  @Override
  public void setReserves(Map<String, Double> reserves) {
    this.reserves = reserves; 
  }

}
