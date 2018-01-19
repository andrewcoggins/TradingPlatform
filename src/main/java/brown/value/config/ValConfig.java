package brown.value.config; 

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.generator.IValuationGenerator;
import brown.value.valuation.library.ValuationType;

/**
 * A value configuration is a combination of tradeables, 
 * the scheme by which they are valued, and 
 * the generator from which values are drawn.
 */
public class ValConfig {
  
  public Set<ITradeable> allGoods; // amy wants to get rid of this! (i.e., put it somewhere else)
  public ValuationType valueScheme;
  
  //TODO: state of the world. 
  //can't store this. 
  public IValuationGenerator generator; 
  
  /**
   * For kryo
   * DO NOT USE
   */
  public ValConfig() { 
    this.allGoods = null; 
    this.valueScheme = ValuationType.None;
    this.generator = null;
  }
  
  public ValConfig(Set<ITradeable> allGoods, ValuationType type, IValuationGenerator generator) { 
    this.allGoods = allGoods; 
    this.valueScheme = type;
    this.generator = generator;
  }
  
}