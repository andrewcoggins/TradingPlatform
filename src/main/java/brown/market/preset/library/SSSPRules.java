package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.OneShotTermination;
import brown.rules.library.SSSPActivity;
import brown.rules.library.SSSPAnonymous;
import brown.rules.library.SSSPQuery;
import brown.rules.library.SSSPAllocation;
import brown.rules.library.SSSPPayment;
import brown.rules.library.XRoundTermination;

public class SSSPRules extends AbsMarketPreset {

  /**
   * some of these are guesses.
   * need to pass in the market internal state, 
   * or otherwise delete it from this constructor.
   */
  public SSSPRules() {
    super(new SSSPAllocation(),
        new SSSPPayment(),
        new SSSPQuery(), 
        new SSSPActivity(),
        new SSSPAnonymous(),
        new OneShotTermination(), 
        new XRoundTermination());
  }
  
}
