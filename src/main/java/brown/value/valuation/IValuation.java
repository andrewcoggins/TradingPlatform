package brown.value.valuation;

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.valuationrepresentation.AbsValuationRepresentation;

public interface IValuation {
  
  public AbsValuationRepresentation getValuation(Set<ITradeable> goods);
  
}