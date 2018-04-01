package brown.rules.library; 

import brown.market.marketstate.IMarketState;
import brown.rules.IPaymentRule;

public class NoPayment implements IPaymentRule {

  @Override
  public void setOrders(IMarketState state) {
    // noop
  }

  @Override
  public void reset() {
    // noop
  }
  
}