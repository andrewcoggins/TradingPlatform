package brown.auction.value.valuation.library;

import java.util.Map;

import brown.auction.value.valuation.IValuation;
import brown.platform.item.ICart;

public class FullValuation extends AbsValuation implements IValuation {

  private Map<ICart, Double> valuation;

  public FullValuation() {
    this.valuation = null;
  }

  public FullValuation(Map<ICart, Double> valuation) {
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
    FullValuation other = (FullValuation) obj;
    if (valuation == null) {
      if (other.valuation != null)
        return false;
    } else if (!valuation.equals(other.valuation))
      return false;
    return true;
  }

}
