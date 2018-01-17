package brown.value.valuation;

import java.util.Set;

import brown.tradeable.library.Good;
import brown.value.valuationrepresentation.AbsValuationRepresentation;


public interface IValuation {
  
  public AbsValuationRepresentation getValuation(Set<Good> goods);
  
}