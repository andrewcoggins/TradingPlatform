package brown.value.valuation;

import brown.tradeable.ITradeable;
import brown.value.valuable.library.Value;

public interface IIndependentValuation extends IValuation {
  
  public Value getValuation(ITradeable good);
    
}