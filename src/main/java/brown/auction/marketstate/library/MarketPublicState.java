package brown.auction.marketstate.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.communication.messages.ITradeMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.item.ICart;

/**
 * Standard MarketState stores the internal information of a market.
 * 
 * @author acoggins
 */
public class MarketPublicState implements IMarketPublicState {

  private int ticks;
  private long time;

  // history
  private List<List<ITradeMessage>> tradeHistory;

  // Allocation rule

  private Map<Integer, List<ICart>> allocation;

  // Payment rule
  private List<IAccountUpdate> payments;

  // activity rule also deals with reserve prices. 
  private Map<String, Double> reserves;


  public MarketPublicState() {
    this.allocation = new HashMap<Integer, List<ICart>>();
    this.payments = new LinkedList<IAccountUpdate>();
    this.time = System.currentTimeMillis();
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

  @Override
  public void setTicks(int ticks) {
    this.ticks = ticks;   
  }

  @Override
  public void setTime(long time) {
    this.time = time;  
  }
  
  @Override
  public String toString() {
    return "MarketPublicState [ticks=" + ticks + ", time=" + time
        + ", tradeHistory=" + tradeHistory + ", allocation=" + allocation
        + ", payments=" + payments + ", reserves=" + reserves + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((allocation == null) ? 0 : allocation.hashCode());
    result = prime * result + ((payments == null) ? 0 : payments.hashCode());
    result = prime * result + ((reserves == null) ? 0 : reserves.hashCode());
    result = prime * result + ticks;
    result = prime * result + (int) (time ^ (time >>> 32));
    result =
        prime * result + ((tradeHistory == null) ? 0 : tradeHistory.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MarketPublicState other = (MarketPublicState) obj;
    if (allocation == null) {
      if (other.allocation != null)
        return false;
    } else if (!allocation.equals(other.allocation))
      return false;
    if (payments == null) {
      if (other.payments != null)
        return false;
    } else if (!payments.equals(other.payments))
      return false;
    if (reserves == null) {
      if (other.reserves != null)
        return false;
    } else if (!reserves.equals(other.reserves))
      return false;
    if (ticks != other.ticks)
      return false;
    if (time != other.time)
      return false;
    if (tradeHistory == null) {
      if (other.tradeHistory != null)
        return false;
    } else if (!tradeHistory.equals(other.tradeHistory))
      return false;
    return true;
  }
  
}
