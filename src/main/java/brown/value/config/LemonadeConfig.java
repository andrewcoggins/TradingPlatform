package brown.value.config; 

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.valuationrepresentation.library.ValuationType;

public class LemonadeConfig extends AbsValueConfig {
  
  public LemonadeConfig(Set<ITradeable> allGoods) { 
    this.allGoods = allGoods; 
    this.valueScheme = ValuationType.None;
    this.aGenerator = null;
  }
  
}