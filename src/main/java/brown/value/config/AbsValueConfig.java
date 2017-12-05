package brown.value.config; 

import java.util.Set;

import brown.tradeable.IValuable;
import brown.value.generator.IValuationGenerator;
import brown.value.valuationrepresentation.library.ValuationType;

public abstract class AbsValueConfig {
  
  public Set<IValuable> allGoods; 
  public ValuationType valueScheme; 
  public IValuationGenerator aGenerator; 
  
}