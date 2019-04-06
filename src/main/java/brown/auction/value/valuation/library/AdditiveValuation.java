
package brown.auction.value.valuation.library;

import java.util.Map;

import brown.auction.value.valuation.IMonotonicValuation;
import brown.platform.item.ISingleItem;


public class AdditiveValuation extends AbsAdditiveValuation implements IMonotonicValuation { 

  public AdditiveValuation() {
    super(); 
  }
  
  public AdditiveValuation(Map<ISingleItem, Double> valuation) {
    super(valuation); 
  }

}
