package brown.auction.value.config.library;

import brown.auction.prevstate.library.BlankStateInfo;
import brown.auction.prevstate.library.PrevStateInfo;
import brown.auction.value.valuation.library.ValuationType;

/**
 * val config for lemonade game.
 * @author acoggins
 *
 */
public class LemonadeConfig extends ValConfig {
  
  public LemonadeConfig() { 
    super(null, ValuationType.Blank);
  }

  @Override
  public PrevStateInfo generateInfo() {
    return new BlankStateInfo();
  }
  
}