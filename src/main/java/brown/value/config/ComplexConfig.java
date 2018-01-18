package brown.value.config;

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.generator.library.NormalValGenerator;
import brown.value.valuation.library.ValuationType;

/**
 * Configuration for complex valuations. 
 * @author andrew
 */
public class ComplexConfig extends ValConfig {
  
  public ComplexConfig(Set<ITradeable> allGoods) {
    super(allGoods, ValuationType.Complex, new NormalValGenerator(x -> (double) x, 1.0));
  }
  
}