package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.LemonadeAnonymous;
import brown.rules.library.OneShotActivity;
import brown.rules.library.OneShotTermination;
import brown.rules.library.SSSPQuery;
import brown.rules.library.SSSPAllocation;
import brown.rules.library.SimpleSecondPrice;
import brown.rules.library.XRoundTermination;

public class SimSPRules extends AbsMarketPreset {

  /**
   * some of these are guesses.
   * need to pass in the market internal state, 
   * or otherwise delete it from this constructor.
   */
  public SimSPRules() {
    super(new SSSPAllocation(),
        new SimpleSecondPrice(),
        new SSSPQuery(), 
        new OneShotActivity(),
        new LemonadeAnonymous(),
        new OneShotTermination(), 
        new XRoundTermination());
  }
  
}
