package brown.auction.rules.library;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IActivityRule;
import brown.platform.messages.TradeMessage;

public class NoBiddingActivity implements IActivityRule {
  @Override
  // Checks if agent has already bid
  public void isAcceptable(IMarketState state, TradeMessage aBid) {
    state.setAcceptable(false);
  }

  @Override
  public void setReserves(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub    
  }

}
