package brown.value.valuationrepresentation.library;

import java.util.Map;
import java.util.Set;

import brown.tradeable.library.Tradeable;
import brown.value.valuable.library.Value;
import brown.value.valuationrepresentation.AbsValuationRepresentation;


public class ComplexValuation extends AbsValuationRepresentation {
  
  public final Map<Set<Tradeable>, Value> vals; 
  
  public ComplexValuation(){ 
    this.vals = null;
  }
  
  public ComplexValuation(Map<Set<Tradeable> , Value> bundle) { 
    this.vals = bundle; 
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
    ComplexValuation other = (ComplexValuation) obj;
    if (vals == null) {
      if (other.vals != null)
        return false;
    } else if (!vals.equals(other.vals))
      return false;
    return true;
  }
  
  
}