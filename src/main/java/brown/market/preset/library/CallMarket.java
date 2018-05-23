package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.CallMarketActivity;
import brown.rules.library.CallMarketInformation;
import brown.rules.library.CallMarketPayment;
import brown.rules.library.CallMarketQuery;
import brown.rules.library.NoAllocation;
import brown.rules.library.NoRecordKeeping;
import brown.rules.library.OneGrouping;
import brown.rules.library.OneShotTermination;
import brown.rules.library.XTimeTermination;

/**
 * A call market is a two-sided market where agents buy
 * and sell securities.
 * @author acoggins
 *
 */
public class CallMarket extends AbsMarketPreset {
  private double seconds;

  public CallMarket(double seconds) {
    super(new NoAllocation(),
        new CallMarketPayment(),
        new CallMarketQuery(), 
        new OneGrouping(),
        new CallMarketActivity(),
        new CallMarketInformation(),
        new OneShotTermination(), 
        new XTimeTermination(seconds),
        new NoRecordKeeping());
    this.seconds = seconds;
  }

  @Override
  public AbsMarketPreset copy() {
    return new CallMarket(this.seconds);
  }
}
