package brown.auction.value.valuation.library;

import brown.auction.value.valuation.ISpecificValuation;
import brown.platform.item.ICart;

public abstract class AbsFakeSATSValuation extends AbsSparseValuation implements ISpecificValuation {
  
  private Queryer queryer; 
  
  public AbsFakeSATSValuation(double aParameter) {
    this.queryer = new Queryer(aParameter); 
  }
  
  public Double getValuation(ICart cart) {
    return this.queryer.query(cart); 
  }
  
  private class Queryer {
    private double param; 
    
    private Queryer(double param) {
      this.param = param;
    }
    
    public double query(ICart cart) {
      return Math.random() * param; 
    }
    
    
  }
}
