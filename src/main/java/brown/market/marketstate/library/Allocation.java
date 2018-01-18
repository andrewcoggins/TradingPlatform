package brown.market.marketstate.library;

import java.util.List;
import java.util.Map;

import brown.market.marketstate.IAllocation;
import brown.tradeable.ITradeable;

public class Allocation implements IAllocation {

  private Map<Integer, List<ITradeable>> alloc; 

  public Allocation(Map<Integer, List<ITradeable>> alloc) {
    this.alloc = alloc;
  }

  @Override
  public Map<Integer, List<ITradeable>> getAllocation() {
    // make this return a flattened list?
    return this.alloc;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((alloc == null) ? 0 : alloc.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
      return (obj instanceof Allocation && ((Allocation) obj).alloc.equals(this.alloc));
  }

  @Override
  public String toString() {
    return "Allocation [" + alloc + "]";
  }

  
}