package brown.rules.terminationconditions.library;

import brown.market.marketstate.ICompleteState;
import brown.rules.terminationconditions.IOuterTC;

public class XRoundTermination implements IOuterTC {
  private static final int maxRuns = 3;
  
@Override
  public void outerTerminated(ICompleteState state) {
    boolean over = state.getOuterRuns() >= maxRuns;
    state.setOuterOver(over);
  }
}
