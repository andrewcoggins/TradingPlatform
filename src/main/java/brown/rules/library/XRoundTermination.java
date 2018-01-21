package brown.rules.library;

import brown.market.marketstate.IMarketState;
import brown.rules.IOuterTC;

public class XRoundTermination implements IOuterTC {
  private static final int maxRuns = 50;
  
@Override
  public void outerTerminated(IMarketState state) {
    boolean over = state.getOuterRuns() >= maxRuns;
    state.setOuterOver(over);
  }
}
