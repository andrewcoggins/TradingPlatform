package brown.value.config;

import brown.market.library.PrevStateInfo;
import brown.value.distribution.library.SpecValDistV2;
import brown.value.valuation.ValuationType;

public class SpecValV2Config extends ValConfig {

  //problem: number of bidders.
  public SpecValV2Config(int numBidders, int bundleSize, int bundleStdDev) {
    super(new SpecValDistV2(numBidders, bundleSize, bundleStdDev), ValuationType.Spectrum);
  }
  
  @Override
  public PrevStateInfo generateInfo() {
    return null;
  }

}
