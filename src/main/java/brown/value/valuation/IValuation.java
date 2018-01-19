package brown.value.valuation;

import java.util.Map;
import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.valuable.library.Value;

public interface IValuation {
  
  public Map<ITradeable, Value> getValuation(Set<ITradeable> goods);
  
}