package brown.rules.library;

import brown.bidbundle.BundleType;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeMessage;
import brown.rules.IActivityRule;

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
