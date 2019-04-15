package brown.auction.value.valuation.library;

import brown.auction.value.valuation.IValuation;
import brown.platform.item.ICart;

/**
 * sparse valuations provide shortcuts for calculating the value of a cart. 
 * implementations may vary. 
 * @author andrewcoggins
 *
 */
public abstract class AbsSparseValuation extends AbsValuation implements IValuation {

  @Override
  public abstract Double getValuation(ICart cart); 

}
