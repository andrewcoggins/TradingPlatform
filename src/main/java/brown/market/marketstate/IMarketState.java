package brown.market.marketstate;

import abrown.misc.library.Allocation;
import abrown.misc.library.Payment;

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