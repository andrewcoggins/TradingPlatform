package brown.auction.rules;

import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.communication.messages.ITradeMessage;
import brown.platform.item.ICart;

/**
 * The activity rule determines what bids and what kinds of 
 * bids are acceptable in a particular market.
 * @author andrew
 *
 */
public interface IActivityRule {
  
  /**
   * determined whether or not a TradeMessage is acceptable for the market. 
   * @param state market internal state. 
   * @param aBid some TradeMessage
   */
  void isAcceptable(IMarketState state, ITradeMessage aBid, List<ITradeMessage> currentBids, 
      ICart items);
 
  /**
   * Handle reserve prices, which may change in some auctions.
   * @param state
   */
  void setReserves(IMarketState state);

}
