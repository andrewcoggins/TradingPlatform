package brown.auction.value.valuation.library;

import brown.auction.value.valuation.ISpecificValuation;
import brown.platform.item.ICart;

public abstract class AbsValuation implements ISpecificValuation {

  @Override
  public abstract Double getValuation(ICart cart);

}
