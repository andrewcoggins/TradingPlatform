package brown.auction.value.distribution;

import java.util.List;
import java.util.Map;

import brown.auction.value.valuation.IValuation;
import brown.platform.tradeable.ITradeable;

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
  
  public Map<String, List<ITradeable>> getTradeableNames();
  
  
}