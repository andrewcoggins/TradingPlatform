package brown.auction.value.config; 

import brown.auction.prevstate.PrevStateInfo;
import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.valuation.ValuationType;

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