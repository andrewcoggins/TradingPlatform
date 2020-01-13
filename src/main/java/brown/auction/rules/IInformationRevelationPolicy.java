package brown.auction.rules;

import brown.auction.marketstate.IMarketPublicState;
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
  void updatePublicState(IMarketState state, IMarketPublicState publicState);


}
