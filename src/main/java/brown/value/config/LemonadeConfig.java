package brown.value.config; 

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.valuation.library.ValuationType;

public class LemonadeConfig extends ValConfig {
  
  public LemonadeConfig(Set<ITradeable> allGoods) { 
    super(allGoods, ValuationType.Simple, null);
  }
  
}