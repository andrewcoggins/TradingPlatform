package brown.market.marketstate;

import abrown.misc.Allocation;
import abrown.misc.Payment;

/**
 * a tentative allocation. 
 * 
 * @author andrew
 *
 */
public interface IMarketState {
  
  public Allocation getAllocation();
  
  public Payment getPayments();
  
  public void setAllocation(Allocation allocation);
  
  public void setPayments(Payment payment);
  
}