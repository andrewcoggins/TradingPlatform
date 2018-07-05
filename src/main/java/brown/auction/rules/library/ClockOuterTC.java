package brown.auction.rules.library;

import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IOuterTC;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.accounting.Order;

/**
 * Outer termination for a clock auction. 
 * one auction must have transpired with some allocation and payment.
 * @author kerry
 *
 */
public class ClockOuterTC implements IOuterTC {

  @Override
  public void outerTerminated(IMarketState state) {
    boolean over = false;
    if (state.getTicks() != 0) {
      over = true;
      Map<ITradeable,List<Integer>> alloc = state.getAltAlloc();
      for (ITradeable t : alloc.keySet()){
        if (alloc.get(t).size() > 1){        
          over = false;
        }
      }
      List<Order> orders = state.getPayments();
      if (orders.size() == 0){
        over = false;
      }
    }
    state.setOuterOver(over);
  }

}
