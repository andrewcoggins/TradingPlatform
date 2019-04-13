package brown.auction.value.valuation.library;

import brown.auction.value.valuation.IValuation;
import brown.logging.library.ErrorLogging;
import brown.platform.item.ICart;
import brown.platform.item.IItem;

public class DiminishingMarginalValuation extends AbsSparseValuation implements IValuation {

  private double baseValue; 
  private double factor; 
  
  public DiminishingMarginalValuation(double baseValue, double factor) {
    if (factor > 1) {
      ErrorLogging.log("ERROR: diminishing marginal valuation factor should be less than 1");
    } else {
      this.baseValue = baseValue; 
      this.factor = factor;  
    }
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


  @Override
  public String toString() {
    return "DiminishingMarginalValuation [baseValue=" + baseValue + ", factor="
        + factor + "]";
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(baseValue);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(factor);
    result = prime * result + (int) (temp ^ (temp >>> 32));
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
    DiminishingMarginalValuation other = (DiminishingMarginalValuation) obj;
    if (Double.doubleToLongBits(baseValue) != Double
        .doubleToLongBits(other.baseValue))
      return false;
    if (Double.doubleToLongBits(factor) != Double
        .doubleToLongBits(other.factor))
      return false;
    return true;
  }
  
}
