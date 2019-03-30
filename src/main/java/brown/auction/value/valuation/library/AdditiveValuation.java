
package brown.auction.value.valuation.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.auction.value.valuation.IMonotonicValuation;
import brown.communication.bid.ICart;
import brown.communication.bid.IItem;
import brown.communication.bid.ISingleItem;
import brown.platform.tradeable.ITradeable;

/**
 * additive valuation specifies a valuation over goods, 
 * where values are additive.
 * @author andrew
 */
public class AdditiveValuation extends AbsAdditiveValuation implements IMonotonicValuation { 

  private final Map<ITradeable, ISingleItem> tradeableToItem; 
  
  // for kryo
  public AdditiveValuation() {
    super(null); 
    this.tradeableToItem = null; 
  }
  
  /**
   * additive valuation takes in a mapping from values to 
   * individual tradeables.
   * 
   * @param valuation
   */
  public AdditiveValuation(Map<ISingleItem, Double> valuation) {
    super(valuation); 
    this.tradeableToItem = new HashMap<ITradeable, ISingleItem>(); 
    for (ISingleItem item: valuation.keySet()) {
      ITradeable t = item.getItem(); 
      this.tradeableToItem.put(t, item); 
    }
  }
  
  @Override
  public Double getValuation(ICart cart) {
    Double value = 0.0; 
    List<IItem> allItems = cart.getItems(); 
    for(IItem item : allItems) {  
      ITradeable t = item.getItem(); 
      value += this.valuation.get(this.tradeableToItem.get(t)) * item.getItemCount(); 
    }
    return value;
  }

  
}
