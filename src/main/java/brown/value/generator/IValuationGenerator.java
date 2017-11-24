package brown.value.generator; 

import java.util.Set;

import brown.tradeable.library.Tradeable;
import brown.value.valuable.library.Value;


public interface IValuationGenerator {
  
  public Value makeValuation(Tradeable good);
  
  public Value makeValuation(Set<Tradeable> goods);
  
  
}