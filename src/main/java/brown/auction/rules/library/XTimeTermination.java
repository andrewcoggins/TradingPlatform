package brown.auction.rules.library;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IInnerTC;

/**
 * Outer termination condition that is dependent on time. 
 * measured in seconds.
 * @author acoggins
 *
 */
public class XTimeTermination implements IInnerTC {
  private final long mill;
  
  /**
   * input number of seconds.
   * @param seconds
   */
  public XTimeTermination(double seconds) {
    this.mill = (long) (seconds * 1000);
  }

  
@Override
  public void innerTerminated(IMarketState state) {
    boolean over = (System.currentTimeMillis() - state.getTime()) > mill;
    state.setInnerOver(over);
  }


@Override
public void reset() {
  // TODO Auto-generated method stub
}

}
