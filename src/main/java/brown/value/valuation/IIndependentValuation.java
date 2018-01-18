package brown.value.valuation;

import brown.tradeable.library.MultiTradeable;
import brown.value.valuable.library.Value;

public interface IIndependentValuation extends IValuation {
  
  public Value getValuation(MultiTradeable good);
    
}