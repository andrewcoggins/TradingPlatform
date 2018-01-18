package abrown.misc;

import java.util.List;
import java.util.Map;

import brown.tradeable.library.MultiTradeable;

/**
 * IAllocation is the interface for allocations.
 * Allocations store a mapping from Tradeables
 * to the IDs of agents that possess them 
 * or will tentatively possess them.
 * 
 * @author andrew
 */
public interface IAllocation {
  
  /**
   * Gets the allocation. 
   * This is returned as a map from a list 
   * of tradeables to integers. 
   * @return
   */
  public Map<List<MultiTradeable>, Integer> getAllocation();
  
}