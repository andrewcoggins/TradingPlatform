package brown.value.valuationrepresentation.library;

import java.util.Map;
import java.util.Set;

import brown.tradeable.library.Tradeable;
import brown.value.valuable.library.Value;
import brown.value.valuationrepresentation.AbsValuationRepresentation;


public class ComplexValuation extends AbsValuationRepresentation {
  
  public final Map<Set<Tradeable>, Value> vals; 
  
  public ComplexValuation(Map<Set<Tradeable> , Value> bundle) { 
    this.vals = bundle; 
  }
  
  
}