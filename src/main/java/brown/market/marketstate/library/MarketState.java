package brown.market.marketstate.library;

import abrown.misc.library.Allocation;
import abrown.misc.library.Payment;
import brown.market.marketstate.IMarketState;

public class MarketState implements IMarketState {

  private Allocation allocation; 
  private Payment payment; 
  
  public MarketState() {
//    TODO
//    this.allocation = new Allocation();
//    this.payment = new Payment();
  }
  
  @Override
  public Allocation getAllocation() {
    return this.allocation;
  }

  @Override
  public Payment getPayments() {
    // TODO Auto-generated method stub
    return this.payment;
  }

  @Override
  public void setAllocation(Allocation allocation) {
    // TODO Auto-generated method stub
  }

  @Override
  public void setPayments(Payment payment) {
    // TODO Auto-generated method stub
  }
  
}