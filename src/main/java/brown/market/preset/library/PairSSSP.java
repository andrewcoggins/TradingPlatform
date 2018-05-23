package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.NoRecordKeeping;
import brown.rules.library.OneShotTermination;
import brown.rules.library.PairGrouping;
import brown.rules.library.OneShotActivity;
import brown.rules.library.SSSPAnonymous;
import brown.rules.library.SimpleQuery;
import brown.rules.library.HighestPriceAllocation;
import brown.rules.library.SimpleSecondPricePayment;
import brown.rules.library.XRoundTermination;

/**
 * Simple Simultaneous Second price auction
 * with pair grouping.
 * @author acoggins
 *
 */
public class PairSSSP extends AbsMarketPreset {
  private int numRuns;

  public PairSSSP(int numRuns) {
    super(new HighestPriceAllocation(),
        new SimpleSecondPricePayment(),
        new SimpleQuery(), 
        new PairGrouping(),
        new OneShotActivity(),
        new SSSPAnonymous(),
        new OneShotTermination(), 
        new XRoundTermination(numRuns),
        new NoRecordKeeping());
    this.numRuns = numRuns;
  }

  @Override
  public AbsMarketPreset copy() {
    return new PairSSSP(this.numRuns);
  }
  
}
