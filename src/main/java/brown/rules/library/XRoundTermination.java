package brown.rules.library;

import brown.market.marketstate.ICompleteState;
import brown.rules.IOuterTC;

public class XRoundTermination implements IOuterTC {
  private static final int maxRuns = 3;
  
@Override
  public void outerTerminated(ICompleteState state) {
    boolean over = state.getOuterRuns() >= maxRuns;
    state.setOuterOver(over);
  }
}
