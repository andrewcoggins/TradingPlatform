package brown.auction.value.valuation.library;

import java.util.Map;

import brown.auction.value.valuation.IMonotonicValuation;
import brown.communication.bid.ISingleItem;

public abstract class AbsAdditiveValuation implements IMonotonicValuation {

  protected final Map<ISingleItem, Double> valuation; 
  
  public AbsAdditiveValuation(Map<ISingleItem, Double> valuation) {
    this.valuation = valuation; 
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
    AbsAdditiveValuation other = (AbsAdditiveValuation) obj;
    if (valuation == null) {
      if (other.valuation != null)
        return false;
    } else if (!valuation.equals(other.valuation))
      return false;
    return true;
  }


  @Override
  public String toString() {
    return "AbsAdditiveValuation [valuation=" + valuation + "]";
  }
}
