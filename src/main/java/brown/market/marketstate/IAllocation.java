package brown.market.marketstate;

import java.util.List;
import java.util.Map;

import brown.tradeable.ITradeable;

/**
 * IAllocation is the interface for allocations.
 * Allocations store a mapping from IDs of agents
 * to a list of ITradeables that agents are to be 
 * allocated
 * 
 * @author kerry
 */
public interface IAllocation {
  
  /**
   * Gets the allocation. 
   * This is returned as a map from a list 
   * of tradeables to integers. 
   * @return
   */
  public Map<Integer, List<ITradeable>> getAllocation();
  
}