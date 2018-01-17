package brown.rules.terminationconditions.library;

import brown.market.marketstate.ICompleteState;
import brown.rules.terminationconditions.IOuterTC;

public class ThreeRoundTermination implements IOuterTC {

@Override
  public void outerTerminated(ICompleteState state) {
    state.incrementOuter();
    boolean over = state.getOuterRuns() >= 3;
    state.setOuterOver(over);
  }
}
