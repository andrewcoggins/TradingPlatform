package brown.auction.preset;

import brown.auction.rules.library.HPReserveAllocation;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneGrouping;
import brown.auction.rules.library.OneShotActivity;
import brown.auction.rules.library.OneShotTermination;
import brown.auction.rules.library.SSSPAnonymous;
import brown.auction.rules.library.SimpleQuery;
import brown.auction.rules.library.SimpleSecondPricePayment;

/**
 * Simple simultaneous second price auction with reserve prices.
 * @author acoggins
 *
 */
public class SSSPReserveRules extends AbsMarketPreset {
  public SSSPReserveRules() {
    super(new HPReserveAllocation(),
        new SimpleSecondPricePayment(),
        new SimpleQuery(), 
        new OneGrouping(),
        new OneShotActivity(),
        new SSSPAnonymous(),
        new OneShotTermination(), 
        new NoRecordKeeping());
  }
  
  @Override
  public AbsMarketPreset copy() {
    return new SSSPReserveRules();
  }
}