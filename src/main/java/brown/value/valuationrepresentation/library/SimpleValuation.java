package brown.value.valuationrepresentation.library;

import java.util.Map;

import brown.tradeable.ITradeable;
import brown.value.valuable.library.Value;
import brown.value.valuationrepresentation.AbsValuationRepresentation;

public class SimpleValuation extends AbsValuationRepresentation {

  public final Map<ITradeable, Value> vals;
  
  public SimpleValuation() {
    this.vals = null;
  }
  
  public SimpleValuation(Map<ITradeable, Value> vals) {
    this.vals = vals; 
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((vals == null) ? 0 : vals.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SimpleValuation other = (SimpleValuation) obj;
    if (vals == null) {
      if (other.vals != null)
        return false;
    } else if (!vals.equals(other.vals))
      return false;
    return true;
  }
  
}