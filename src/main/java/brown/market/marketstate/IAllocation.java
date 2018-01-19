package brown.market.marketstate;

import java.util.List;
import java.util.Map;

import brown.tradeable.ITradeable;

/**
 * IAllocation is the interface for allocations
 * An Allocation stores a map from Agents to Lists of Tradeables
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