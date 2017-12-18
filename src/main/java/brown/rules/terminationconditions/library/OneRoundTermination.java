package brown.rules.terminationconditions.library;

import brown.market.marketstate.IMarketState;
import brown.rules.terminationconditions.IOuterTC;

public class OneRoundTermination implements IOuterTC {

@Override
  public void outerTerminated(IMarketState state) {
    state.incrementOuter();
    boolean over = state.getOuterRuns() >= 1;
    state.setOuterOver(over);
  }
}
