package brown.value.generator; 

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.valuable.library.Value;

public abstract class AbsValuationGenerator {
  
  public abstract Value makeValuation(ITradeable good);
  
  //this should go away
  public abstract Value makeValuation(Set<ITradeable> goods);
  
}