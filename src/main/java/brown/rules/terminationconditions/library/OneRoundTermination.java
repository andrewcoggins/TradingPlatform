package brown.rules.terminationconditions.library;

import brown.market.marketstate.ICompleteState;
import brown.rules.terminationconditions.IOuterTC;

public class OneRoundTermination implements IOuterTC {

@Override
  public void outerTerminated(ICompleteState state) {
    state.incrementOuter();
    boolean over = state.getOuterRuns() >= 1;
    state.setOuterOver(over);
  }
}
