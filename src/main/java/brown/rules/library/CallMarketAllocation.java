package brown.rules.library;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.market.marketstate.IMarketState;
import brown.market.marketstate.library.BuyOrder;
import brown.market.marketstate.library.OrderBook;
import brown.market.marketstate.library.SellOrder;
import brown.rules.IAllocationRule;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;

public class CallMarketAllocation implements IAllocationRule {

  @Override
  public void setAllocation(IMarketState state) {
  // null for now
  }

  @Override
  public void reset() {
  }

}
