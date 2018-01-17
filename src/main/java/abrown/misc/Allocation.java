package abrown.misc;

import java.util.List;
import java.util.Map;

import brown.tradeable.library.Tradeable;

public class Allocation implements IAllocation {

private Map<List<Tradeable>, Integer> alloc; 

public Allocation(Map<List<Tradeable>, Integer> alloc) {
  this.alloc = alloc;
}

@Override
public Map<List<Tradeable>, Integer> getAllocation() {
  return this.alloc;
}


}