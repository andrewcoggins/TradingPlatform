package brown.rules;

import brown.market.marketstate.ICompleteState;
import brown.messages.library.TradeMessage;

public interface IActivityRule {

  public void isAcceptable(ICompleteState state, TradeMessage aBid);
 
  // handle reserves with this
  public void setReserves();

  public void reset();
  
}
