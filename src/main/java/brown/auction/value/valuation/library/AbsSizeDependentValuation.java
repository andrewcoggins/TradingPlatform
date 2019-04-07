package brown.auction.value.valuation.library;

import brown.auction.value.valuation.IValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;

public abstract class AbsSizeDependentValuation extends AbsSparseValuation implements IValuation {

  private double baseValue; 
  private double factor; 
  
  public AbsSizeDependentValuation(double baseValue, double factor) {
    this.baseValue = baseValue; 
    this.factor = factor; 
  }
  
  
  public Double getValuation(ICart cart) {
    double count = 0; 
    double value = 0.0; 
    
    for (IItem item : cart.getItems()) {
      count += item.getItemCount(); 
    }
    
    for (int i = 0; i < count; i++) {
      value += baseValue; 
      baseValue *= factor; 
    }
    
    return value; 
  }
  
}
