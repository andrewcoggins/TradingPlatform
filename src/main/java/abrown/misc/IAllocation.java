package abrown.misc;

import java.util.List;
import java.util.Map;

import brown.tradeable.library.Tradeable;

/**
 * IAllocation is the interface for allocations. 
 * Allocations store a mapping from Tradeables
 * to the IDs of agents that possess them 
 * or will tentatively posess them.
 * 
 * @author andrew
 *
 */
public interface IAllocation {
  
  /**
   * Gets the allocatiion. 
   * This is returned as a map from a list 
   * of tradeables to integers. 
   * @return
   */
  public Map<List<Tradeable>, Integer> getAllocation();
  
}