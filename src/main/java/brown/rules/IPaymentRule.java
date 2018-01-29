package brown.rules;

import brown.market.marketstate.IMarketState;

public interface IPaymentRule {

  public void setOrders(IMarketState state);

  public void reset();  
}
