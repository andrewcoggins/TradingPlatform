package brown.auction.preset;

import brown.auction.rules.library.ClockActivity;
import brown.auction.rules.library.ClockAllocation;
import brown.auction.rules.library.ClockInformation;
import brown.auction.rules.library.ClockPayment;
import brown.auction.rules.library.ClockQuery;
import brown.auction.rules.library.ClockTC;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneGrouping;

/**
 * Implementation of a clock auction. 
 * @author acoggins
 *
 */
public class ClockAuction extends AbsMarketPreset {
  private double increment;
  
  public ClockAuction(double increment) {
    super(new ClockAllocation(),
        new ClockPayment(),
        new ClockQuery(), 
        new OneGrouping(),
        new ClockActivity(increment),
        new ClockInformation(),
        new ClockTC(),
        new NoRecordKeeping());
    this.increment = increment;
  }

  @Override
  public AbsMarketPreset copy() {
    return new ClockAuction(this.increment);
  }

}
