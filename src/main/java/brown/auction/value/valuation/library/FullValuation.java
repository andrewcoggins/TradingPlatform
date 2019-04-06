package brown.auction.value.valuation.library;

import java.util.Map;

import brown.auction.value.valuation.IValuation;
import brown.platform.item.ICart;

public class FullValuation extends AbsFullValuation implements IValuation {

  public FullValuation() {
    super(); 
  }
  
  public FullValuation(Map<ICart, Double> valuation) {
    super(valuation); 
  }
}
