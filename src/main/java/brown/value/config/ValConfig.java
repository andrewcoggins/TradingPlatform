package brown.value.config; 

import brown.market.library.PrevStateInfo;
import brown.value.distribution.IValuationDistribution;
import brown.value.valuation.ValuationType;

/**
 * a value configuration tells the server the distribution from which valuations are drawn, 
 * and what tradeables are given valuations..
 * @author acoggins
 *
 */
public abstract class ValConfig {
  
  public final IValuationDistribution valueDistribution;
  public final ValuationType type; 
  
  /**
   * For kryo
   * DO NOT USE
   */
  public ValConfig() { 
    this.valueDistribution = null;
    this.type = null;
  }
  
  public ValConfig(ValuationType type) { 
    this.valueDistribution = null;
    this.type = type; 
  }
  
  public ValConfig(IValuationDistribution valueDistribution, ValuationType type) { 
    this.valueDistribution = valueDistribution;
    this.type = type; 
  }
  
  public abstract PrevStateInfo generateInfo();
  
}