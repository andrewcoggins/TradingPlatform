package brown.auction.value.distribution.library;

import java.util.List;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.valuation.ISpecificValuation;
import brown.auction.value.valuation.library.Lab05Valuation;
import brown.platform.item.ICart;

/**
 * A simplified version of the TACValuationDistribution- fewer items. 
 * @author andrewcoggins
 *
 */
public class Lab05ValuationDistribution extends AbsValuationDistribution implements IValuationDistribution {

  /**
   * For kryo DO NOT USE
   */
  public Lab05ValuationDistribution() {
    super(null, null);
  }
  
  public Lab05ValuationDistribution(ICart items, List<IValuationGenerator> generators) {
    super(items, generators); 
  }
  
  @Override
  public ISpecificValuation sample(Integer agentID, List<List<Integer>> agentGroups) {
    int idx = 0;
    for (List<Integer> lst : agentGroups) {
    	int j = lst.indexOf(agentID);
    	if (j >= 0) {
    		idx = j % 4;
    		break;
    	}
    }
    
    return new Lab05Valuation(idx);
  }
}
