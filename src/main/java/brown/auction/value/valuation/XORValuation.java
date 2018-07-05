package brown.auction.value.valuation; 

import java.util.Map;
import java.util.Set;

import brown.mechanism.tradeable.ComplexTradeable;
import brown.mechanism.tradeable.ITradeable;

/**
 * Gives an XOR valuation over bundles of goods.
 * @author acoggins
 *
 */
public class XORValuation implements IBundleValuation {

  private final Map<ComplexTradeable, Double> valueParams; 
  
  /**
   * for kryo do not use
   */
  public XORValuation() {
    this.valueParams = null; 
  }
  
  /**
   * The value parameters for XOR valuation
   * are a map from sets of simple tradeables to associated values.
   * @param valueParams
   */
  public XORValuation(Map<ComplexTradeable, Double> valueParams) {
    this.valueParams = valueParams; 
  }
  
  @Override
  public Double getValuation(ITradeable tradeable) {
    if (this.valueParams.containsKey(tradeable)){
      return this.valueParams.get(tradeable);
    } else {
      return -1.;
    }
  }
  
  public Set<ComplexTradeable> getTradeables() { 
    return this.valueParams.keySet();
  }

  @Override
  public IValuation safeCopy() {
    return new XORValuation(this.valueParams);
  }

  @Override
  public String toString() {
    return "XORValuation [valueParams=" + valueParams + "]";
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
    XORValuation other = (XORValuation) obj;
    if (valueParams == null) {
      if (other.valueParams != null)
        return false;
    } else if (!valueParams.equals(other.valueParams))
      return false;
    return true;
  }
  
  
}