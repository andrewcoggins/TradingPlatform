package brown.auction.rules.library; 

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IPaymentRule;

public class NoPayment implements IPaymentRule {

  @Override
  public void setOrders(IMarketState state) {
    // noop
  }
  
}