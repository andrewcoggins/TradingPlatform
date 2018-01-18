package brown.value.valuation;

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.valuationrepresentation.IValuationRepresentation;

public interface IValuation {
  
  public IValuationRepresentation getValuation(Set<ITradeable> goods);
  
}