package brown.value.config; 

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.generator.AbsValuationGenerator;
import brown.value.valuation.library.ValuationType;

/**
 * A value configuration is a combination of tradeables, 
 * the scheme by which they are valued, and 
 * the generator from which values are drawn.
 */
public class ValConfig {
  
  public Set<ITradeable> allGoods; 
  public ValuationType valueScheme;
  
  //TODO: state of the world. 
  //can't store this. 
  public AbsValuationGenerator generator; 
  
  /**
   * For kryo
   * DO NOT USE
   */
  public ValConfig() { 
    this.allGoods = null; 
    this.valueScheme = ValuationType.None;
    this.generator = null;
  }
  
  public ValConfig(Set<ITradeable> allGoods, ValuationType type, AbsValuationGenerator generator) { 
    this.allGoods = allGoods; 
    this.valueScheme = type;
    this.generator = generator;
  }
  
}