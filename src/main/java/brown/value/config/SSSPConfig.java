package brown.value.config; 

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.generator.library.NormalGenerator;
import brown.value.valuation.library.ValuationType;

/**
 * simulates a simple simultaneous auction where valuations of goods 
 * are normally distributed.
 * @author acoggins
 *
 */
public class SSSPConfig extends AbsValueConfig {
  
  public SSSPConfig(Set<ITradeable> allGoods) {
    super(allGoods, ValuationType.Simple, new NormalGenerator(x -> (double) x, 1.0));
  }
  
}