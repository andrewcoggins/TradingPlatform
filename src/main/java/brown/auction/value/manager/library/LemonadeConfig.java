package brown.auction.value.manager.library;

import brown.auction.prevstate.library.BlankStateInfo;
import brown.auction.prevstate.library.PrevStateInfo;
import brown.auction.value.valuation.library.ValuationType;

/**
 * val manager for lemonade game.
 * @author acoggins
 *
 */
public class LemonadeConfig extends ValuationManager {
  
  public LemonadeConfig() { 
    super(null, ValuationType.Blank);
  }

  @Override
  public PrevStateInfo generateInfo() {
    return new BlankStateInfo();
  }
  
}