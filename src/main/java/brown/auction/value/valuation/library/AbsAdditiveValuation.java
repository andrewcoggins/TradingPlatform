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

}
