package brown.auction.value.valuation.library;

import brown.auction.value.valuation.ISpecificValuation;
import brown.platform.item.ICart;

/**
 * sparse valuations provide shortcuts for calculating the value of a cart. 
 * implementations may vary. 
 * @author andrewcoggins
 *
 */
public abstract class AbsSparseValuation extends AbsValuation implements ISpecificValuation {

  @Override
  public abstract Double getValuation(ICart cart); 

}
