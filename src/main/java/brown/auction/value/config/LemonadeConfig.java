package brown.auction.value.config;

import brown.auction.prevstate.BlankStateInfo;
import brown.auction.prevstate.PrevStateInfo;
import brown.auction.value.valuation.ValuationType;

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