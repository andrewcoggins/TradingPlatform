package brown.rules;

import brown.market.marketstate.IMarketState;

/**
 * Payment rule determines how agents pay for allocated goods.
 * @author andrew
 *
 */
public interface IPaymentRule {

  /**
   * Sets orders that will modify agent accounts 
   * by adding/subtracting goods and money
   * @param state market state
   */
  public void setOrders(IMarketState state);

  /**
   * resets 
   */
  public void reset();  
}
