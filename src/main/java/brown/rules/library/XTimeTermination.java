package brown.rules.library;

import brown.market.marketstate.IMarketState;
import brown.rules.IOuterTC;

public class XTimeTermination implements IOuterTC {
  private final long mill;
  
  public XTimeTermination(double seconds) {
    this.mill = (long) (seconds * 1000);
  }

  
@Override
  public void outerTerminated(IMarketState state) {
    boolean over = (System.currentTimeMillis() - state.getTime()) > mill;
    state.setOuterOver(over);
  }
}
