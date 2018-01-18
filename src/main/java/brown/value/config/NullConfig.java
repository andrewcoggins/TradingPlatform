package brown.value.config; 

import brown.value.valuation.library.ValuationType;

public class NullConfig extends ValConfig {
  
  public NullConfig() { 
    super(null, ValuationType.None, null);
  }
  
}