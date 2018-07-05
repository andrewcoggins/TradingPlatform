package brown.auction.preset;

import brown.auction.rules.library.ClockActivity;
import brown.auction.rules.library.ClockAllocation;
import brown.auction.rules.library.ClockInformation;
import brown.auction.rules.library.ClockOuterTC;
import brown.auction.rules.library.ClockPayment;
import brown.auction.rules.library.ClockQuery;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneGrouping;
import brown.auction.rules.library.OneShotTermination;

/**
 * Implementation of a clock auction. 
 * @author acoggins
 *
 */
public class ClockAuction extends AbsMarketPreset {
  double increment;
  
  public ClockAuction(double increment) {
    super(new ClockAllocation(),
        new ClockPayment(),
        new ClockQuery(), 
        new OneGrouping(),
        new ClockActivity(increment),
        new ClockInformation(),
        new OneShotTermination(), 
        new ClockOuterTC(),
        new NoRecordKeeping());
    this.increment = increment;
  }

  @Override
  public AbsMarketPreset copy() {
    return new ClockAuction(this.increment);
  }

}
