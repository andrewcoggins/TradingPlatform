package brown.value.config; 

import brown.value.valuationrepresentation.library.ValuationType;

public class NullConfig extends AbsValueConfig {
  
  public NullConfig() { 
    this.allGoods = null; 
    this.valueScheme = ValuationType.None;
    this.aGenerator = null;
  }
  
}