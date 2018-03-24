package brown.value.config;

import java.util.Set;

import brown.market.library.PrevStateInfo;
import brown.tradeable.ITradeable;
import brown.value.distribution.library.SpecValDistribution;
import brown.value.valuation.ValuationType;

/**
 * Configuration for complex valuations. 
 * @author andrew
 */
public class SpecVCGValConfig extends ValConfig {
  
  //problem: number of bidders.
  public SpecVCGValConfig(Set<ITradeable> allGoods, int numBundles, int bundleSizeMean, int bundleSizeStdDev) {
    super(new SpecValDistribution(numBundles, bundleSizeMean, bundleSizeStdDev), ValuationType.Spectrum);
  }

  @Override
  public PrevStateInfo generateInfo() {
    //noop
    return null; 
  }
}
