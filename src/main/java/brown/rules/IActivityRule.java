package brown.rules;

import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeMessage;

public interface IActivityRule {

  public void isAcceptable(IMarketState state, TradeMessage aBid);
 
  // handle reserve prices with this
  public void setReserves(IMarketState state);

  public void reset();
  
}
