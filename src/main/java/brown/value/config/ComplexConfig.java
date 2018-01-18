package brown.value.config;

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.generator.library.NormalGenerator;
import brown.value.valuationrepresentation.library.ValuationType;

/**
 * configuration for complex valuations. 
 * @author andrew
 *
 */
public class ComplexConfig extends AbsValueConfig {
  
  public ComplexConfig(Set<ITradeable> allGoods) {
    super(allGoods, ValuationType.Complex, new NormalGenerator(x -> (double) x, 1.0));
  }
}