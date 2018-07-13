package brown.auction.rules.library; 

import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.ITerminationCondition;
import brown.mechanism.tradeable.ITradeable;


/**
 * Outer termination for a clock auction. 
 * one auction must have transpired with some allocation and payment.
 * @author kerry
 *
 */
public class ClockTC implements ITerminationCondition {

  @Override
  public void isTerminated(IMarketState state) {
    boolean over = false;
    if (state.getTicks() != 0) {
      over = true;
      Map<ITradeable,List<Integer>> alloc = state.getAltAlloc();
      for (ITradeable t : alloc.keySet()) {
        if (alloc.get(t).size() > 1){    
          over = false;
        }
      }
    }
    state.setOver(over);
  }

}