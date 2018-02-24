package brown.value.config;

import java.util.Set;

import brown.market.library.PrevStateInfo;
import brown.tradeable.ITradeable;
import brown.value.valuation.ValuationType;

/**
 * Configuration for complex valuations. 
 * @author andrew
 */
public class ComplexConfig extends ValConfig {
  
  //NOT FUNCTIONAL
  public ComplexConfig(Set<ITradeable> allGoods) {
    super(null, ValuationType.Auction);
  }

  @Override
  public PrevStateInfo generateInfo() {
    return null;
  }
}
