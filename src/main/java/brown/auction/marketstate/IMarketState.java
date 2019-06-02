package brown.auction.marketstate;

import java.util.List;
import java.util.Map;

import brown.communication.messages.IInformationMessage;
import brown.communication.messages.library.TradeRequestMessage;
import brown.platform.accounting.library.AccountUpdate;
import brown.platform.item.ICart;
/**
 * Stores the internal state of a market as it runs. Consists of a series of
 * getters and setters for the various fields of the market state.
 * 
 * @author acoggins
 */
public interface IMarketState {


  public Double getFlatIncrement();

  public void setFlatIncrement(Double increment);

  // Allocation rule
  public Map<Integer, List<ICart>> getAllocation();

  public void setAllocation(Map<Integer, List<ICart>> allocation);

  // Payment rule
  public void setPayments(List<AccountUpdate> payment);

  public List<AccountUpdate> getPayments();

  // orders (this is from the payment rule)
  // delete this !!! just use set Payments
  public void clearOrders();

  // Query rule
  public TradeRequestMessage getTRequest();

  public void setTRequest(TradeRequestMessage t);

  // Activity rule
  public boolean getAcceptable();

  public void setAcceptable(boolean b);

  // IR policy
  public Map<Integer, List<IInformationMessage>> getReport();

  public void setReport(Map<Integer, List<IInformationMessage>> gameReport);

  // Termination condition
  public long getTime();

  public void tick();

  public int getTicks();

  public boolean isOpen();

  public void close();


}
