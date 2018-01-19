package brown.value.generator; 

import brown.tradeable.ITradeable;
import brown.value.valuable.library.Value;

public interface IValuationGenerator {
  
  public abstract Value makeValuation(ITradeable good);
  
}