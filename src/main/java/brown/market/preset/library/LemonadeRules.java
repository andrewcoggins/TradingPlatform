package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.LemonadeAllocation;
import brown.rules.library.LemonadeAnonymous;
import brown.rules.library.LemonadePayment;
import brown.rules.library.LemonadeQuery;
import brown.rules.library.OneShotActivity;
import brown.rules.library.OneShotTermination;
import brown.rules.library.XRoundTermination;

public class LemonadeRules extends AbsMarketPreset {

  /**
   * some of these are guesses.
   * need to pass in the market internal state, 
   * or otherwise delete it from this constructor.
   */
  public LemonadeRules() {
    super(new LemonadeAllocation(),
        new LemonadePayment(), 
        new LemonadeQuery(),
        new OneShotActivity(),
        new LemonadeAnonymous(), 
        new OneShotTermination(),
        new XRoundTermination());
  }
  
}
