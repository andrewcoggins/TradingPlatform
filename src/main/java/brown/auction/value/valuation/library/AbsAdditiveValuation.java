package brown.auction.value.valuation.library;

import java.util.Map;

import brown.auction.value.valuation.IMonotonicValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.ISingleItem;
import brown.platform.item.library.SingleItem;

public abstract class AbsAdditiveValuation extends AbsSparseValuation implements IMonotonicValuation {
  
  private Map<ISingleItem, Double> singleItemMapping; 
  
  public AbsAdditiveValuation() {
    this.singleItemMapping = null;
  }

  public AbsAdditiveValuation(Map<ISingleItem, Double> valuation) {
    this.singleItemMapping = valuation; 
  }
  
  
  public Double getValuation(ICart cart) {
     Double value = 0.0; 
     for (IItem item : cart.getItems()) {
       ISingleItem sItem = new SingleItem(item.getName()); 
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
    AbsAdditiveValuation other = (AbsAdditiveValuation) obj;
    if (singleItemMapping == null) {
      if (other.singleItemMapping != null)
        return false;
    } else if (!singleItemMapping.equals(other.singleItemMapping))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "AbsAdditiveValuation [singleItemMapping=" + singleItemMapping + "]";
  }


}
