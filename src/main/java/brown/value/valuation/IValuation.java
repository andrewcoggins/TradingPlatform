package brown.value.valuation;

import java.util.Set;

import brown.tradeable.library.Tradeable;
import brown.value.valuationrepresentation.AbsValuationRepresentation;


public interface IValuation {
  
  public AbsValuationRepresentation getValuation(Set<Tradeable> goods);
  
}