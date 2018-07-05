package brown.auction.preset;

import brown.auction.rules.library.HighestPriceAllocation;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneShotActivity;
import brown.auction.rules.library.OneShotTermination;
import brown.auction.rules.library.PairGrouping;
import brown.auction.rules.library.SSSPAnonymous;
import brown.auction.rules.library.SimpleQuery;
import brown.auction.rules.library.SimpleSecondPricePayment;
import brown.auction.rules.library.XRoundTermination;

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
