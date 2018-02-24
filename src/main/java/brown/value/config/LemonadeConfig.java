package brown.value.config;

import brown.market.library.PrevStateInfo;
import brown.value.valuation.ValuationType;

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
    return null;
  }
  
}