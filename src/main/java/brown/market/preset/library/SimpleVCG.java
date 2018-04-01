package brown.market.preset.library; 

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.NoRecordKeeping;
import brown.rules.library.OneGrouping;
import brown.rules.library.OneShotComplexActivity;
import brown.rules.library.OneShotTermination;
import brown.rules.library.SSSPAnonymous;
import brown.rules.library.SimpleQuery;
import brown.rules.library.VCGAllocation;
import brown.rules.library.VCGPayment;
import brown.rules.library.XRoundTermination;

public class SimpleVCG extends AbsMarketPreset {
  private int numRuns;
  
  
  public SimpleVCG(int numRuns) {
    super(new VCGAllocation(),
        new VCGPayment(),
        new SimpleQuery(),
        new OneGrouping(),
        new OneShotComplexActivity(),
        new SSSPAnonymous(),
        new OneShotTermination(),
        new XRoundTermination(numRuns),
        new NoRecordKeeping());
    this.numRuns = numRuns;
  }

  @Override
  public AbsMarketPreset copy() {
    return new SimpleVCG(this.numRuns);
  }
}