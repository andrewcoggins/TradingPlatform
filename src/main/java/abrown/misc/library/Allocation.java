package abrown.misc.library;

import java.util.List;
import java.util.Map;

import abrown.misc.IAllocation;
import brown.tradeable.ITradeable;
import brown.tradeable.library.MultiTradeable;

public class Allocation implements IAllocation {

  private Map<List<ITradeable>, Integer> alloc; 

  public Allocation(Map<List<ITradeable>, Integer> alloc) {
    this.alloc = alloc;
  }

  @Override
  public Map<List<ITradeable>, Integer> getAllocation() {
    return this.alloc;
  }

}