package brown.value.config; 

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.generator.AbsValuationGenerator;
import brown.value.valuationrepresentation.library.ValuationType;

/*
 * A value configuration is a combination of what valuables are to be traded on, the scheme by which
 * they are valued, and the generator from which values are drawn.
 */
public abstract class AbsValueConfig {
  
  public Set<ITradeable> allGoods; 
  public ValuationType valueScheme; 
  public AbsValuationGenerator aGenerator; 
  
  public AbsValueConfig(Set<ITradeable> allGoods){
    this.allGoods = allGoods; 
    this.valueScheme = ValuationType.None; 
    this.aGenerator = null;    
  }
  
  public AbsValueConfig(Set<ITradeable> allGoods, ValuationType valueScheme, AbsValuationGenerator aGenerator) {
    this.allGoods = allGoods; 
    this.valueScheme = valueScheme; 
    this.aGenerator = aGenerator; 
  }
}