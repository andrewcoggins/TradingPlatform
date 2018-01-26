package brown.rules.library;

import brown.market.marketstate.IMarketState;
import brown.rules.IOuterTC;

public class XRoundTermination implements IOuterTC {
  private final int numRuns;;
  
  public XRoundTermination(int numRuns) {
    this.numRuns = numRuns;
  }

  
@Override
  public void outerTerminated(IMarketState state) {
    boolean over = state.getOuterRuns() >= numRuns;
    state.setOuterOver(over);
  }
}
