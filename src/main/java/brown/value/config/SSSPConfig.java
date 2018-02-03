package brown.value.config; 

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.distribution.library.AdditiveValuationDistribution;
import brown.value.generator.library.LabTwoValGenerator;
import brown.value.valuation.ValuationType;

/**
 * Configuration for simple valuations. 
 * @author acoggins
 */
public class SSSPConfig extends ValConfig {
  
  public SSSPConfig(Set<ITradeable> allGoods) {
    super(new AdditiveValuationDistribution(new LabTwoValGenerator(), allGoods), ValuationType.Auction);
  }
  
}