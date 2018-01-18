package brown.market.marketstate.library;

import brown.market.marketstate.IMarketState;

public class MarketState implements IMarketState {

  private Allocation allocation; 
  private Payment payment; 
  
  public MarketState() {
    this.allocation = null;
    this.payment = null;
  }
  
  @Override
  public Allocation getAllocation() {
    return this.allocation;
  }

  @Override
  public Payment getPayments() {
    return this.payment;
  }

  @Override
  public void setAllocation(Allocation allocation) {
    this.allocation = allocation;
  }

  @Override
  public void setPayments(Payment payment) {
    this.payment = payment;
  }
  
}