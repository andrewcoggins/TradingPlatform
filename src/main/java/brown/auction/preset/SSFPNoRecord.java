package brown.auction.preset;

import brown.auction.rules.library.HighestPriceAllocation;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneGrouping;
import brown.auction.rules.library.OneShotActivity;
import brown.auction.rules.library.OneShotTermination;
import brown.auction.rules.library.SSSPAnonymousPolicy;
import brown.auction.rules.library.SimpleFirstPricePayment;
import brown.auction.rules.library.SimpleQuery;

/**
 * rules for a SSFP Server.
 * @author acoggins
 *
 */
public class SSFPNoRecord extends AbsMarketPreset {

  public SSFPNoRecord() {
    super(new HighestPriceAllocation(),
        new SimpleFirstPricePayment(),
        new SimpleQuery(), 
        new OneGrouping(),
        new OneShotActivity(),
        new SSSPAnonymousPolicy(),
        new OneShotTermination(), 
        new NoRecordKeeping());
  }

  @Override
  public AbsMarketPreset copy() {
    return new SSFPNoRecord();
  }
  
}
