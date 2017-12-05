package brown.value.generator; 

import java.util.Set;

import brown.tradeable.library.Tradeable;
import brown.value.valuable.library.Value;


public abstract class AbsValuationGenerator {
  
  public abstract Value makeValuation(Tradeable good);
  
  public abstract Value makeValuation(Set<Tradeable> goods);
  
}