package brown.auction.value.manager.library;

import brown.auction.prevstate.library.PrevStateInfo;
import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.valuation.library.ValuationType;

/**
 * a value configuration tells the server the distribution from which valuations are drawn, 
 * and what tradeables are given valuations..
 * @author acoggins
 *
 */
public abstract class ValuationManager {
  
  public final IValuationDistribution valueDistribution;
  public final ValuationType type; 
  
  /**
   * For kryo
   * DO NOT USE
   */
  public ValuationManager() {
    this.valueDistribution = null;
    this.type = null;
  }
  
  public ValuationManager(ValuationType type) {
    this.valueDistribution = null;
    this.type = type; 
  }
  
  public ValuationManager(IValuationDistribution valueDistribution, ValuationType type) {
    this.valueDistribution = valueDistribution;
    this.type = type; 
  }
  
  public abstract PrevStateInfo generateInfo();
  
}