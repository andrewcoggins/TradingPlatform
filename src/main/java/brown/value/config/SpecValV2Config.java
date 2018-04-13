package brown.value.config;

import brown.market.library.PrevStateInfo;
import brown.value.distribution.library.SpecValDistV2;
import brown.value.valuation.ValuationType;

public class SpecValV2Config extends ValConfig {
  public int nBundles;
  public int meanSize;
  public int stDev;
  
  //problem: number of bidders.
  public SpecValV2Config(int nBundles, int meanSize, int stDev) {
    super(new SpecValDistV2(), ValuationType.Spectrum);
    this.nBundles = nBundles;
    this.meanSize = meanSize;
    this.stDev = stDev;
  }
  
  @Override
  public PrevStateInfo generateInfo() {
    return null;
  }

}
