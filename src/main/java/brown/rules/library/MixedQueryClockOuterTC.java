package brown.rules.library;

import java.util.List;
import java.util.Map;

import brown.logging.Logging;
import brown.market.marketstate.IMarketState;
import brown.market.marketstate.library.Order;
import brown.rules.IOuterTC;
import brown.tradeable.ITradeable;

public class MixedQueryClockOuterTC implements IOuterTC{

  @Override
  public void outerTerminated(IMarketState state) {
    boolean over = false;
    if (state.getTicks() %2 == 0 && state.getTicks() != 0){
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
    Logging.log("MixedQueryClockOuterTC: " + over +", ticks: " + state.getTicks());
    state.setOuterOver(over);
  }
}
