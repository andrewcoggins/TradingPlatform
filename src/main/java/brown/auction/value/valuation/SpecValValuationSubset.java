package brown.auction.value.valuation;

import java.util.HashMap;
import java.util.Map;

import brown.mechanism.tradeable.ComplexTradeable;
import brown.mechanism.tradeable.ITradeable;

/**
 * Completely escribes some subset of the total spectrum auction valuation. 
 * Essentially an XOR valuation.
 * @author acoggins
 *
 */
public class SpecValValuationSubset implements IBundleValuation {
  private final Map<ComplexTradeable, Double> valuation;
  
  // for kryo
  public SpecValValuationSubset() {
    this.valuation = null;
  }
  
  public SpecValValuationSubset(Map<ComplexTradeable, Double> valuation) {
    this.valuation = valuation; 
  }
    
  @Override
  public Double getValuation(ITradeable tradeable) {
    if (this.valuation.containsKey(tradeable)){
      return this.valuation.get(tradeable);
    } else {
      return -1.;
    }
  }
  
  public Map<ComplexTradeable, Double> getAllValuations() {
    Map<ComplexTradeable, Double> newVal = new HashMap<ComplexTradeable,Double>();
    for (ComplexTradeable t: this.valuation.keySet()){
      newVal.put(t, this.valuation.get(t));
    }
    return newVal;
  }

  @Override
  public IValuation safeCopy() {
    Map<ComplexTradeable, Double> newVal = new HashMap<ComplexTradeable,Double>();
    for (ComplexTradeable t: this.valuation.keySet()){
      newVal.put(t, this.valuation.get(t));
    }
    return new SpecValValuationSubset(newVal);
  }

}
