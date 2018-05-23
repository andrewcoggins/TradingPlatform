package brown.rules;

import brown.market.marketstate.IMarketState;

/**
 * Determines what information is revealed to agent, and what information 
 * is passed between auctions in a sequential simulation.
 * @author andrew
 *
 */
public interface IInformationRevelationPolicy {

  /**
   * determines what the game report should be
   * @param state market internal state. 
   */
  public void setReport(IMarketState state);

  /**
   * resets all stored information. 
   */
  public void reset();

  /**
   * construct some summary state for use in a future auction. 
   * @param state market internal state.
   */
  public void constructSummaryState(IMarketState state);
}
