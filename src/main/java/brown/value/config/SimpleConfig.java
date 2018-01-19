package brown.value.config; 

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.andrew.distribution.library.AdditiveValuationDistribution;
import brown.value.andrew.valuation.ValuationType;
import brown.value.generator.library.NormalValGenerator;

/**
 * Configuration for simple valuations. 
 * @author acoggins
 */
public class SimpleConfig extends ValConfig {
  
  public SimpleConfig(Set<ITradeable> allGoods) {
    super(new AdditiveValuationDistribution(new NormalValGenerator(1.0, 1.0), allGoods), ValuationType.Auction);
  }
  
}