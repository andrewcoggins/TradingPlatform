
package brown.auction.value.valuation.library;

import java.util.Map;

import brown.auction.value.valuation.ISpecificValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Item;

public class AdditiveValuation extends AbsSparseValuation
    implements ISpecificValuation {

  private Map<IItem, Double> singleItemMapping;

  public AdditiveValuation() {
    this.singleItemMapping = null;
  }

  public AdditiveValuation(Map<IItem, Double> valuation) {
    this.singleItemMapping = valuation;
  }

  public Double getValuation(ICart cart) {
    Double value = 0.0;
    for (IItem item : cart.getItems()) {
      IItem sItem = new Item(item.getName());
      value += this.singleItemMapping.get(sItem) * item.getItemCount();
    }
    return value;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((singleItemMapping == null) ? 0 : singleItemMapping.hashCode());
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
    if (singleItemMapping == null) {
      if (other.singleItemMapping != null)
        return false;
    } else if (!singleItemMapping.equals(other.singleItemMapping))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "AdditiveValuation [singleItemMapping=" + singleItemMapping + "]";
  }

}
