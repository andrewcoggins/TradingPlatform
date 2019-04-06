package brown.auction.value.valuation.library;

import brown.auction.value.valuation.IValuation;
import brown.platform.item.ICart;

public abstract class AbsValuation implements IValuation {
  
  
  @Override
  public abstract Double getValuation(ICart cart);

}
