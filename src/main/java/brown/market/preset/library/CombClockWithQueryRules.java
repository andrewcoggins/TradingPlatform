package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.MixedQueryClockActivity;
import brown.rules.library.MixedQueryClockAllocation;
import brown.rules.library.MixedQueryClockInformation;
import brown.rules.library.MixedQueryClockOuterTC;
import brown.rules.library.MixedQueryClockPayment;
import brown.rules.library.MixedQueryClockQuery;
import brown.rules.library.NoAllocation;
import brown.rules.library.NoPayment;
import brown.rules.library.NoRecordKeeping;
import brown.rules.library.OneGrouping;
import brown.rules.library.OneShotTermination;
import brown.rules.library.QueryRoundActivity;
import brown.rules.library.QueryRoundInformation;
import brown.rules.library.QueryRoundQuery;
import brown.rules.library.XRoundTermination;

public class CombClockWithQueryRules extends AbsMarketPreset {
  double increment;
  
  /**
   * some of these are guesses.
   * need to pass in the market internal state, 
   * or otherwise delete it from this constructor.
   */
  public CombClockWithQueryRules(double increment) {
    super(new MixedQueryClockAllocation(),
        new MixedQueryClockPayment(),
        new MixedQueryClockQuery(), 
        new OneGrouping(),
        new MixedQueryClockActivity(increment),
        new MixedQueryClockInformation(),
        new OneShotTermination(), 
        new MixedQueryClockOuterTC(),
        new NoRecordKeeping());
    this.increment = increment;
  }

  @Override
  public AbsMarketPreset copy() {
    return new CombClockWithQueryRules(this.increment);
  }
}
