package brown.value.andrew.distribution;

import brown.value.andrew.valuation.IValuation;

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