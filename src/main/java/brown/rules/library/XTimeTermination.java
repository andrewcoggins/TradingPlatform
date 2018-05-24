package brown.rules.library;

import brown.market.marketstate.IMarketState;
import brown.rules.IOuterTC;

/**
 * Outer termination condition that is dependent on time. 
 * measured in seconds.
 * @author acoggins
 *
 */
public class XTimeTermination implements IOuterTC {
  private final long mill;
  
  /**
   * input number of seconds.
   * @param seconds
   */
  public XTimeTermination(double seconds) {
    this.mill = (long) (seconds * 1000);
  }

  
@Override
  public void outerTerminated(IMarketState state) {
    boolean over = (System.currentTimeMillis() - state.getTime()) > mill;
    state.setOuterOver(over);
  }
}
