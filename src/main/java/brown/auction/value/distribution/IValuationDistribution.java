package brown.auction.value.distribution;

import brown.auction.value.valuation.IValuation;

/**
 * IValuationDistribution samples IValuations from a distribution. 
 * @author andrew
 */
public interface IValuationDistribution {
  
  /**
   * samples IValuations from a distribution
   * @return IValuation
   */
  public IValuation sample();
  
}