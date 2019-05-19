package brown.auction.value.valuation.library;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.value.valuation.IGeneralValuation;
import brown.auction.value.valuation.ISpecificValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.ISingleItem;
import brown.platform.item.library.Cart;

public class GeneralValuation implements IGeneralValuation {

  
  private Map<List<ISingleItem>, ISpecificValuation> specificValuationMap; 
  
  public GeneralValuation(Map<List<ISingleItem>, ISpecificValuation> specificValuations) {
    this.specificValuationMap = specificValuations; 
  }
  
  @Override
  public Double getValuation(ICart cart) {
    Double totalValue = 0.0;  
    for(List<ISingleItem> singleItemList : this.specificValuationMap.keySet()) {
      List<IItem> subItems = new LinkedList<IItem>(); 
      for (ISingleItem singleItem : singleItemList) {
        if (cart.containsItem(singleItem.getName())) {
          subItems.add(cart.getItemByName(singleItem.getName())); 
        }
      }
      ICart subCart = new Cart(subItems); 
      totalValue += this.specificValuationMap.get(singleItemList).getValuation(subCart); 
    }
    return totalValue;
  }

  @Override
  public String toString() {
    return "GeneralValuation [specificValuationMap=" + specificValuationMap
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((specificValuationMap == null) ? 0
        : specificValuationMap.hashCode());
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
    GeneralValuation other = (GeneralValuation) obj;
    if (specificValuationMap == null) {
      if (other.specificValuationMap != null)
        return false;
    } else if (!specificValuationMap.equals(other.specificValuationMap))
      return false;
    return true;
  }
  
  
}
