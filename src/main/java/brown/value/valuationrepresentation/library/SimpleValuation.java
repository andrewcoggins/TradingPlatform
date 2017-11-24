package brown.value.valuationrepresentation.library;

import java.util.Map;

import brown.tradeable.library.Tradeable;
import brown.value.valuable.library.Value;
import brown.value.valuationrepresentation.AbsValuationRepresentation;


public class SimpleValuation extends AbsValuationRepresentation {
  
  public final Map<Tradeable, Value> vals;
  
  public SimpleValuation(Map<Tradeable, Value> vals) {
    this.vals = vals; 
  }
  
}