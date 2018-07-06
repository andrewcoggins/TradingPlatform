package brown.auction.value.valuation.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.auction.value.valuation.IMonotonicValuation;
import brown.auction.value.valuation.IValuation;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;

/**
 * additive valuation specifies a valuation over goods, 
 * where values are additive.
 * @author andrew
 *
 */
public class AdditiveValuation implements IMonotonicValuation { 

  private final Map<SimpleTradeable, Double> valueParams; 
  
  
  // for kryo
  public AdditiveValuation(){
    this.valueParams = null;
  }
  
  /**
   * additive valuation takes in a mapping from values to 
   * individual tradeables.
   * @param valueParams
   */
  public AdditiveValuation(Map<SimpleTradeable, Double> valueParams) {
    this.valueParams = valueParams; 
  }
  
  @Override
  public Double getValuation(ITradeable tradeable) {
    Double value = 0.0; 
    List<SimpleTradeable> allTradeables = tradeable.flatten(); 
    for(SimpleTradeable atom : allTradeables) {  
      value = value + this.valueParams.get(atom); 
    }
    return value;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((valueParams == null) ? 0 : valueParams.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AdditiveValuation other = (AdditiveValuation) obj;
    if (valueParams == null) {
      if (other.valueParams != null)
        return false;
    } else if (!valueParams.equals(other.valueParams))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "AdditiveValuation [valueParams=" + valueParams + "]";
  }

  @Override
  public IValuation safeCopy() {
    Map<SimpleTradeable, Double> newValueParams = new HashMap<SimpleTradeable,Double>();
    for (SimpleTradeable t: this.valueParams.keySet()){
      newValueParams.put(t, this.valueParams.get(t));
    }
    return new AdditiveValuation(newValueParams);
  }
  
  
}