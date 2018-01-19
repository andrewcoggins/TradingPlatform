package brown.rules;

import brown.market.marketstate.ICompleteState;

public interface IPaymentRule {

  public void setOrders(ICompleteState state);

  public void permitShort(ICompleteState state);

  public void reset();  
}
