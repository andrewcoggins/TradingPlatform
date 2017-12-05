package brown.value.config; 

import java.util.Set;


import brown.tradeable.IValuable;
import brown.value.generator.IValuationGenerator;
import brown.value.valuationrepresentation.library.ValuationType;

/*
 * A value configuration is a combination of what valuables are to be traded on, the scheme by which
 * they are valued, and the generator from which values are drawn. Maybe also an IR policy of some sort. 
 */
public abstract class AbsValueConfig {
  
  public Set<IValuable> allGoods; 
  public ValuationType valueScheme; 
  public IValuationGenerator aGenerator; 
  
}