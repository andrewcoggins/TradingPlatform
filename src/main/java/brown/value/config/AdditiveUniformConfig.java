package brown.value.config; 

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.distribution.library.AdditiveValuationDistribution;
import brown.value.generator.library.UniformValGenerator;
import brown.value.valuation.ValuationType;

/**
 * Configuration for simple valuations. 
 * @author acoggins
 */
public class AdditiveUniformConfig extends ValConfig {
  
  public AdditiveUniformConfig(Set<ITradeable> allGoods) {
    super(new AdditiveValuationDistribution(new UniformValGenerator(), allGoods), ValuationType.Auction);
  }
  
}