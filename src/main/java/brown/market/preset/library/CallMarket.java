package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.BlankAllocation;
import brown.rules.library.CallMarketActivity;
import brown.rules.library.CallMarketAllocation;
import brown.rules.library.CallMarketPayment;
import brown.rules.library.CallMarketQuery;
import brown.rules.library.NoRecordKeeping;
import brown.rules.library.OneGrouping;
import brown.rules.library.OneShotTermination;
import brown.rules.library.RecordBids;
import brown.rules.library.SimpleFirstPricePayment;
import brown.rules.library.SimpleSecondPriceActivity;
import brown.rules.library.SSSPAnonymous;
import brown.rules.library.SimpleSimultaneousQuery;
import brown.rules.library.HighestPriceAllocation;
import brown.rules.library.XRoundTermination;

public class CallMarket extends AbsMarketPreset {

  /**
   * some of these are guesses.
   * need to pass in the market internal state, 
   * or otherwise delete it from this constructor.
   */
  public CallMarket(int numRuns, String filePath) {
    super(new BlankAllocation(),
        new CallMarketPayment(),
        new CallMarketQuery(), 
        new OneGrouping(),
        new CallMarketActivity(),
        new SSSPAnonymous(),
        new OneShotTermination(), 
        new XRoundTermination(numRuns),
        new NoRecordKeeping());
  }
  
}
