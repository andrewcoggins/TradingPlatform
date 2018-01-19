package brown.value.valuationrepresentation;

import java.util.Map;

import brown.tradeable.ITradeable;
import brown.value.valuable.library.Value;

public interface IValuationRepresentation {
  
  public Map<ITradeable, Value> getValuation();
  
}