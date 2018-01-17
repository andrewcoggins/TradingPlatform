package brown.value.valuation;

import brown.tradeable.library.Tradeable;
import brown.value.valuable.library.Value;

public interface IIndependentValuation extends IValuation {
  
  public Value getValuation(Tradeable good);
    
}