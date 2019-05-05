package brown.auction.rules.termination.onesided;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.ITerminationCondition;

public class OneShotTermination extends AbsRule implements ITerminationCondition {

  @Override
  public void isTerminated(IMarketState state) {
    if (state.getTicks() > 0) {
      state.setOver(true);
    }
  }
}
