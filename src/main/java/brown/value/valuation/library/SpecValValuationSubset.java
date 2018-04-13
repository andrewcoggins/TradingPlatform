package brown.value.valuation.library;

import java.util.HashMap;
import java.util.Map;

import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.value.valuation.IValuation;

public class SpecValValuationSubset implements IValuation{
  private final Map<ComplexTradeable, Double> valuation;
  
  // for kryo
  public SpecValValuationSubset(){
    this.valuation = null;
  }
  
  /**
   * additive valuation takes in a mapping from values to 
   * individual tradeables.
   * @param valueParams
   */
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
  
  public Map<ComplexTradeable, Double> getAllValuations(){
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
