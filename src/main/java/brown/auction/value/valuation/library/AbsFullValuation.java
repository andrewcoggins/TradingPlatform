package brown.auction.value.valuation.library;

import java.util.Map;

import brown.auction.value.valuation.IValuation;
import brown.platform.item.ICart;

/**
 * all full valuations provide a complete mapping from every cart to a value for that cart. 
 * use with caution. 
 * @author andrewcoggins
 *
 */
public abstract class AbsFullValuation extends AbsValuation implements IValuation {

  private Map<ICart, Double> valuation; 
  
  public AbsFullValuation() {
    this.valuation = null;
  }
  
  public AbsFullValuation(Map<ICart, Double> valuation) {
    this.valuation = valuation; 
  }
  
  @Override
  public Double getValuation(ICart cart) {
    return this.valuation.get(cart); 
  }

  @Override
  public String toString() {
    return "AbsFullValuation [valuation=" + valuation + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((valuation == null) ? 0 : valuation.hashCode());
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
    AbsFullValuation other = (AbsFullValuation) obj;
    if (valuation == null) {
      if (other.valuation != null)
        return false;
    } else if (!valuation.equals(other.valuation))
      return false;
    return true;
  }

  
  
}
