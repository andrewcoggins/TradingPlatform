package brown.value.config; 

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.distribution.library.AdditiveValuationDistribution;
import brown.value.generator.library.NormalValGenerator;
import brown.value.valuation.ValuationType;

/**
 * Configuration for simple valuations. 
 * @author acoggins
 */
public class SimpleConfig extends ValConfig {
  
  public SimpleConfig(Set<ITradeable> allGoods) {
    super(new AdditiveValuationDistribution(new NormalValGenerator(1.0, 1.0), allGoods), ValuationType.Auction);
  }
  
}