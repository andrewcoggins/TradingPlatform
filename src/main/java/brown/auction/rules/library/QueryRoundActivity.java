package brown.auction.rules.library;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IActivityRule;
import brown.mechanism.bidbundle.BundleType;
import brown.platform.messages.TradeMessage;

public class QueryRoundActivity implements IActivityRule {

  @Override
  public void isAcceptable(IMarketState state, TradeMessage aBid) {
    state.setAcceptable(aBid.Bundle.getType() == BundleType.QUERY);
  }

  @Override
  public void setReserves(IMarketState state) {
  }

  @Override
  public void reset() {
  }

}
