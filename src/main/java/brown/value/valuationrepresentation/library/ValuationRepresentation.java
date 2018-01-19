package brown.value.valuationrepresentation.library;

import java.util.Map;

import brown.tradeable.ITradeable;
import brown.value.valuable.library.Value;
import brown.value.valuationrepresentation.IValuationRepresentation;

public class ValuationRepresentation implements IValuationRepresentation {

  public final Map<ITradeable, Value> valuation;
  
  public ValuationRepresentation() {
    this.valuation = null;
  }
  
  public ValuationRepresentation(Map<ITradeable, Value> valuation) {
    this.valuation = valuation; 
  }
  
  public Map<ITradeable, Value> getValuation() {
    return this.valuation;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((valuation == null) ? 0 : valuation.hashCode());
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
    ValuationRepresentation other = (ValuationRepresentation) obj;
    if (valuation == null) {
      if (other.valuation != null)
        return false;
    } else if (!valuation.equals(other.valuation))
      return false;
    return true;
  }

}