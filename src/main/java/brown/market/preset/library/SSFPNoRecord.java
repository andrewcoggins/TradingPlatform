package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.NoRecordKeeping;
import brown.rules.library.OneGrouping;
import brown.rules.library.OneShotTermination;
import brown.rules.library.SimpleFirstPricePayment;
import brown.rules.library.OneShotActivity;
import brown.rules.library.SSSPAnonymous;
import brown.rules.library.SimpleSimultaneousQuery;
import brown.rules.library.HighestPriceAllocation;
import brown.rules.library.XRoundTermination;

/**
 * rules for a SSFP Server.
 * @author acoggins
 *
 */
public class SSFPNoRecord extends AbsMarketPreset {
  private int numRuns;

  public SSFPNoRecord(int numRuns) {
    super(new HighestPriceAllocation(),
        new SimpleFirstPricePayment(),
        new SimpleSimultaneousQuery(), 
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
    return new SSFPNoRecord(this.numRuns);
  }
  
}
