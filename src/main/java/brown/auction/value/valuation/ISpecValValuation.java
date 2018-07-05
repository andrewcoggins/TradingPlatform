package brown.auction.value.valuation;

import java.util.Map;

import brown.mechanism.tradeable.ComplexTradeable;

/**
 * Extends the IValuation interface for spectrum auction
 * valuations.
 * @author acoggins
 *
 */
public interface ISpecValValuation extends IValuation {
  
  /**
   * Queries the value for some bundle.
   * @param t a complexTradeable
   * @return the value for that tradeable.
   */
  public Double queryValue(ComplexTradeable t);
  
  /**
   * generates some XOR bids per the specval generator.
   * @param nBids the number of bids to be generated
   * @param meanSize the mean size of the bundles generated.
   * @param stdev the standard deviation size of the bundles generated.
   * @return a map from bundles to their values.
   */
  public Map<ComplexTradeable, Double> generateXORBids(int nBids, int meanSize, int stdev);

}
