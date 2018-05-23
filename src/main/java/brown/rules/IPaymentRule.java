package brown.rules;

import brown.market.marketstate.IMarketState;

/**
 * Payment rule determines how agents pay for allocated goods.
 * @author andrew
 *
 */
public interface IPaymentRule {

  public void setOrders(IMarketState state);

  public void reset();  
}
