package brown.auction.rules;

import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.communication.messages.ITradeMessage;

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
  void setOrders(IMarketState state, List<ITradeMessage> tradeMessages);

}
