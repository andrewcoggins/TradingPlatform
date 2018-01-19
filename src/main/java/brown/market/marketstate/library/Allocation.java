package brown.market.marketstate.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.market.marketstate.IAllocation;
import brown.tradeable.ITradeable;

/**
* An allocation stores a map from Agents to Lists of Tradeables
* @author andrew
*/
public class Allocation implements IAllocation {

  private Map<Integer, List<ITradeable>> allocation; 

  public Allocation(){
    this.allocation = new HashMap<Integer, List<ITradeable>>();
  }
  
  public Allocation(Map<Integer, List<ITradeable>> allocation) {
    this.allocation = allocation;
  }

  @Override
  public Map<Integer, List<ITradeable>> getAllocation() {
    return this.allocation;
  }

  @Override
  public String toString() {
    return "Allocation [" + allocation + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((allocation == null) ? 0 : allocation.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
      return (obj instanceof Allocation && ((Allocation) obj).allocation.equals(this.allocation));
  }

}