package brown.rules;

import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeMessage;

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
  public void isAcceptable(IMarketState state, TradeMessage aBid);
 
  /**
   * Handle reserve prices, which may change in some auctions.
   * @param state
   */
  public void setReserves(IMarketState state);

  /**
   * resets all stored information.
   */
  public void reset();
  
}
