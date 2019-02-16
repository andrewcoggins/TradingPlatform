
package brown.auction.value.valuation.library;

import java.util.List;
import java.util.Map;

import brown.auction.value.valuation.IMonotonicValuation;
import brown.platform.tradeable.ITradeable;
import brown.platform.tradeable.library.SimpleTradeable;

/**
 * additive valuation specifies a valuation over goods, 
 * where values are additive.
 * @author andrew
 */
public class AdditiveValuation implements IMonotonicValuation { 

  private final Map<ITradeable, Double> valuation; 
  
  // for kryo
  public AdditiveValuation() {
    this.valuation = null;
  }
  
  /**
   * additive valuation takes in a mapping from values to 
   * individual tradeables.
   * @param valuation
   */
  public AdditiveValuation(Map<ITradeable, Double> valuation) {
    this.valuation = valuation; 
  }
  
  @Override
  public Double getValuation(ITradeable tradeable) {
    Double value = 0.0; 
    List<SimpleTradeable> allTradeables = tradeable.flatten(); 
    for(SimpleTradeable atom : allTradeables) {  
      value = value + this.valuation.get(atom); 
    }
    return value;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((valuation == null) ? 0 : valuation.hashCode());
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
    if (valuation == null) {
      if (other.valuation != null)
        return false;
    } else if (!valuation.equals(other.valuation))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "AdditiveValuation [valuation=" + valuation + "]";
  }
  
}
