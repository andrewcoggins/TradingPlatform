package brown.auction.preset;

import brown.auction.rules.library.CallMarketActivity;
import brown.auction.rules.library.CallMarketInformation;
import brown.auction.rules.library.CallMarketPayment;
import brown.auction.rules.library.CallMarketQuery;
import brown.auction.rules.library.NoAllocation;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneGrouping;
import brown.auction.rules.library.OneShotTermination;
import brown.auction.rules.library.XTimeTermination;

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
