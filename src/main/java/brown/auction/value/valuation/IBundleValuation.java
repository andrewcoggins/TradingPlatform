package brown.auction.value.valuation;

import java.util.Map;

import brown.mechanism.tradeable.library.ComplexTradeable;

/**
 * An IBundleValuation specifies a valuation over ComplexValuations
 * @author acoggins
 *
 */
public interface IBundleValuation extends IMonotonicValuation {
   
  public Map<ComplexTradeable, Double> getAllValuations(); 
}