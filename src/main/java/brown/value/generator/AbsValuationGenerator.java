package brown.value.generator; 

import java.util.Set;

import brown.tradeable.library.Good;
import brown.value.valuable.library.Value;


public abstract class AbsValuationGenerator {
  
  public abstract Value makeValuation(Good good);
  
  public abstract Value makeValuation(Set<Good> goods);
  
}