package brown.auction.value.distribution;

import java.util.List;

import brown.auction.value.valuation.ISpecificValuation;

/**
 * IValuationDistribution samples IValuations from a distribution. 
 * @author andrew
 */
public interface IValuationDistribution {
  
  /**
   * samples IValuations from a distribution
   * @return IValuation
   */
  public ISpecificValuation sample(Integer agentID, List<List<Integer>> agentGroups);
  
  public List<String> getItemNames();
  
  
}