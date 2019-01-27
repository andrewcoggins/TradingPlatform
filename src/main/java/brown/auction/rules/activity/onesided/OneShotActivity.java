package brown.auction.rules.activity.onesided;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IActivityRule;
import brown.mechanism.messages.library.TradeMessage;

public class OneShotActivity extends AbsRule implements IActivityRule {

  @Override
  public void isAcceptable(IMarketState state, TradeMessage aBid) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReserves(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

}
