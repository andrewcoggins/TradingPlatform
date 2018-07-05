package brown.auction.rules;

import brown.auction.marketstate.IMarketState;

/**
 * An allocation rule allocates tradeables to agents.
 * @author andrew
 */
public interface IAllocationRule {

  /**
   * Sets an allocation in the market internal state.
   * @param state market state.
   */
  public void setAllocation(IMarketState state);

  /**
   * resets all stored information.
   */
  public void reset();  
}
