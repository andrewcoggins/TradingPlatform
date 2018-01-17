package brown.rules.activityrules;

import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeMessage;

public interface IActivityRule {

  
  public void isAcceptable(IMarketState state, TradeMessage aBid);
 
  // handle reserves with this
  public void setReserves();
  
}
