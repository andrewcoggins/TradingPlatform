package brown.value.valuation.library; 

import java.util.Map;
import java.util.Set;

import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.value.valuation.IBundleValuation;
import brown.value.valuation.IValuation;

public class XORValuation implements IBundleValuation {

  private final Map<ComplexTradeable, Double> valueParams; 
  
  /**
   * for kryo do not use
   */
  public XORValuation() {
    this.valueParams = null; 
  }
  
  public XORValuation(Map<ComplexTradeable, Double> valueParams) {
    this.valueParams = valueParams; 
  }
  
  @Override
  public Double getValuation(ITradeable tradeable) {
    return this.valueParams.get(tradeable);
  }
  
  public Set<ComplexTradeable> getTradeables() { 
    return this.valueParams.keySet();
  }

  @Override
  public IValuation safeCopy() {
    // TODO Auto-generated method stub
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