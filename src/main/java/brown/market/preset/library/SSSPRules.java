package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.OneShotTermination;
import brown.rules.library.SimpleSecondPriceActivity;
import brown.rules.library.SSSPAnonymous;
import brown.rules.library.SimpleSimultaneousQuery;
import brown.rules.library.HighestPriceAllocation;
import brown.rules.library.SimpleSecondPricePayment;
import brown.rules.library.XRoundTermination;

public class SSSPRules extends AbsMarketPreset {

  /**
   * some of these are guesses.
   * need to pass in the market internal state, 
   * or otherwise delete it from this constructor.
   */
  public SSSPRules(int numRuns) {
    super(new HighestPriceAllocation(),
        new SimpleSecondPricePayment(),
        new SimpleSimultaneousQuery(), 
        new SimpleSecondPriceActivity(),
        new SSSPAnonymous(),
        new OneShotTermination(), 
        new XRoundTermination(numRuns));
  }
  
}
