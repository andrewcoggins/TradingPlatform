package brown.value.generator; 

import java.util.Set;

import brown.tradeable.library.MultiTradeable;
import brown.value.valuable.library.Value;


public abstract class AbsValuationGenerator {
  
  public abstract Value makeValuation(MultiTradeable good);
  
  public abstract Value makeValuation(Set<MultiTradeable> goods);
  
}