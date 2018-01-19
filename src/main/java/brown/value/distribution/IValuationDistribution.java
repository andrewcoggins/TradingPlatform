package brown.value.distribution;

import brown.value.valuation.IValuation;

/**
 * IValuationDistribution samples IValuations
 * from distributions. 
 * @author andrew
 *
 */
public interface IValuationDistribution {
  
  /*
   * Samples IValuations from a distribution
   */
  public IValuation sample();
  
  
}