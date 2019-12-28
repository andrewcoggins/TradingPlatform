package brown.auction.rules;

import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.communication.messages.ITradeMessage;

/**
 * An allocation rule allocates tradeables to agents.
 * @author andrew
 */
public interface IUtilityRule {

  /**
   * Sets an allocation in the market internal state.
   * @param state market state.
   */
   void setAllocation(IMarketState state, List<ITradeMessage> messages);

}
