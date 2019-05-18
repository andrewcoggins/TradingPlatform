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
  
}
