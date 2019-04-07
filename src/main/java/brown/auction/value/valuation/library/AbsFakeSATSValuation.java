package brown.auction.value.valuation.library;

import brown.auction.value.valuation.IValuation;
import brown.platform.item.ICart;

public abstract class AbsFakeSATSValuation extends AbsSparseValuation implements IValuation {
  
  private Queryer queryer; 
  
  public AbsFakeSATSValuation(double aParameter) {
    // some params would go here.
    this.queryer = new Queryer(aParameter); 
  }
  
  
  // want to query the sats valuation for bundles. 
  // like... 
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
