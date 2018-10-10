package brown.auction.rules;

import brown.auction.marketstate.IMarketState;

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
  void setReport(IMarketState state);


  /**
   * construct some summary state for use in a future auction. 
   * @param state market internal state.
   */
  void constructSummaryState(IMarketState state);
}
