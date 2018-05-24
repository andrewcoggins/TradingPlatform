package brown.rules.library;

import brown.market.marketstate.IMarketState;
import brown.rules.IOuterTC;

/**
 * Termination that is based on some number of rounds, per a sequential auction.
 * @author acoggins
 *
 */
public class XRoundTermination implements IOuterTC {
  private final int numRuns;
  
  /**
   * The user details some number of runs that the auction
   * goes for.
   * @param numRuns
   */
  public XRoundTermination(int numRuns) {
    this.numRuns = numRuns;
  }

  
@Override
  public void outerTerminated(IMarketState state) {
    boolean over = state.getOuterRuns() >= numRuns;
    state.setOuterOver(over);
  }
}
