package brown.value.amy;

import java.util.Map;

import brown.tradeable.ITradeable;
import brown.value.valuable.library.Value;

public interface AMYIValuationRepn {
  
  public Map<ITradeable, Value> getValuation();

}
