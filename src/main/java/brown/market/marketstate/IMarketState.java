package brown.market.marketstate;

import brown.market.marketstate.library.Allocation;
import brown.market.marketstate.library.Payment;

/**
 * The market state consists of a tentative allocation and tentative payments. 
 * @author andrew
 */
public interface IMarketState {
  
  public Allocation getAllocation();
  
  public Payment getPayments();
  
  public void setAllocation(Allocation allocation);
  
  public void setPayments(Payment payment);
  
}