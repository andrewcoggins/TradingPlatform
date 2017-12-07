package brown.value.config; 

import java.util.Set;

import brown.tradeable.library.Tradeable;
import brown.value.generator.library.NormalGenerator;
import brown.value.valuationrepresentation.library.ValuationType;

/**
 * simulates a simple simultaneous auction where valuations of goods 
 * are normally distributed.
 * @author acoggins
 *
 */
public class SSSPConfig extends AbsValueConfig {
  
  public SSSPConfig(Set<Tradeable> allGoods) {
    this.allGoods = allGoods; 
    this.valueScheme = ValuationType.Simple;
    this.aGenerator = new NormalGenerator(x -> (double) x, 1.0);
  }
}