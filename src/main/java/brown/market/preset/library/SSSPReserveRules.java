package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.HPReserveAllocation;
import brown.rules.library.NoRecordKeeping;
import brown.rules.library.OneGrouping;
import brown.rules.library.OneShotTermination;
import brown.rules.library.OneShotActivity;
import brown.rules.library.SSSPAnonymous;
import brown.rules.library.SimpleQuery;
import brown.rules.library.SimpleSecondPricePayment;
import brown.rules.library.XRoundTermination;

public class SSSPReserveRules extends AbsMarketPreset {
  private int numRuns;
  /**
   * SSSP with reserve prices imposed within the allocation rule.
   * need to pass in the market internal state, 
   * or otherwise delete it from this constructor.
   */
  public SSSPReserveRules(int numRuns) {
    super(new HPReserveAllocation(),
        new SimpleSecondPricePayment(),
        new SimpleQuery(), 
        new OneGrouping(),
        new OneShotActivity(),
        new SSSPAnonymous(),
        new OneShotTermination(), 
        new XRoundTermination(numRuns),
        new NoRecordKeeping());
    this.numRuns = numRuns;
  }
  
  @Override
  public AbsMarketPreset copy() {
    return new SSSPReserveRules(this.numRuns);
  }
}