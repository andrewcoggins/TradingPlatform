package brown.value.config;

import brown.market.library.PrevStateInfo;
import brown.value.distribution.library.SpecValDistV2;
import brown.value.valuation.ValuationType;

public class SpecValV2Config extends ValConfig {

  //problem: number of bidders.
  public SpecValV2Config() {
    super(new SpecValDistV2(), ValuationType.Spectrum);
  }
  
  @Override
  public PrevStateInfo generateInfo() {
    return null;
  }

}
