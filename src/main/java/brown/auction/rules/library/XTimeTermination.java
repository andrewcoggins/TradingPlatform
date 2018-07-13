package brown.auction.rules.library;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.ITerminationCondition;

/**
 * Outer termination condition that is dependent on time. 
 * measured in seconds.
 * @author acoggins
 *
 */
public class XTimeTermination implements ITerminationCondition {
  private final long mill;
  
  /**
   * input number of seconds.
   * @param seconds
   */
  public XTimeTermination(double seconds) {
    this.mill = (long) (seconds * 1000);
  }

  
@Override
  public void isTerminated(IMarketState state) {
    boolean over = (System.currentTimeMillis() - state.getTime()) > mill;
    state.setOver(over);
  }

}
