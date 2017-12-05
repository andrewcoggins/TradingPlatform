package brown.value.config; 

import java.util.Set;

import brown.tradeable.library.Tradeable;
import brown.value.generator.AbsValuationGenerator;
import brown.value.valuationrepresentation.library.ValuationType;

/*
 * A value configuration is a combination of what valuables are to be traded on, the scheme by which
 * they are valued, and the generator from which values are drawn. Maybe also an IR policy of some sort. 
 */
public abstract class AbsValueConfig {
  
  //should be abstract
  public Set<Tradeable> allGoods; 
  public ValuationType valueScheme; 
  //TODO: state of the world. 
  //can't store this. 
  public AbsValuationGenerator aGenerator; 
  
}