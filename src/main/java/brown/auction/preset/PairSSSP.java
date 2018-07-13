package brown.auction.preset;

import brown.auction.rules.library.HighestPriceAllocation;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneShotActivity;
import brown.auction.rules.library.OneShotTermination;
import brown.auction.rules.library.PairGrouping;
import brown.auction.rules.library.SSSPAnonymousPolicy;
import brown.auction.rules.library.SimpleQuery;
import brown.auction.rules.library.SimpleSecondPricePayment;

/**
 * Simple Simultaneous Second price auction
 * with pair grouping.
 * @author acoggins
 *
 */
public class PairSSSP extends AbsMarketPreset {

  public PairSSSP() {
    super(new HighestPriceAllocation(),
        new SimpleSecondPricePayment(),
        new SimpleQuery(), 
        new PairGrouping(),
        new OneShotActivity(),
        new SSSPAnonymousPolicy(),
        new OneShotTermination(), 
        new NoRecordKeeping());
  }

  @Override
  public AbsMarketPreset copy() {
    return new PairSSSP();
  }
  
}
