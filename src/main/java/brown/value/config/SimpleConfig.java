package brown.value.config; 

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.generator.library.NormalValGenerator;
import brown.value.valuation.library.ValuationType;

/**
 * Configuration for simple valuations. 
 * @author acoggins
 */
public class SimpleConfig extends ValConfig {
  
  public SimpleConfig(Set<ITradeable> allGoods) {
    super(allGoods, ValuationType.Simple, new NormalValGenerator(x -> (double) x, 1.0));
  }
  
}