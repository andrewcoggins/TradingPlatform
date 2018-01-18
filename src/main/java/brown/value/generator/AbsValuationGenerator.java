package brown.value.generator; 

import brown.tradeable.ITradeable;
import brown.value.valuable.library.Value;

public abstract class AbsValuationGenerator {
  
  public abstract Value makeValuation(ITradeable good);
  
}