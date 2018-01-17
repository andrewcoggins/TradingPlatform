package brown.value.valuation;

import brown.tradeable.library.Good;
import brown.value.valuable.library.Value;


public interface IIndependentValuation extends IValuation {
  
  public Value getValuation(Good good);
    
}