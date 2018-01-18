package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.activityrules.library.OneShotActivity;
import brown.rules.allocationrules.library.LemonadeAllocation;
import brown.rules.irpolicies.library.LemonadeAnonymous;
import brown.rules.paymentrules.library.LemonadePayment;
import brown.rules.queryrules.library.LemonadeQuery;
import brown.rules.terminationconditions.library.OneShotTermination;
import brown.rules.terminationconditions.library.ThreeRoundTermination;

public class LemonadeRules extends AbsMarketPreset {

  /**
   * some of these are guesses.
   * need to pass in the market internal state, or otherwise delete it from this constructor.
   */
  public LemonadeRules() {
    super(new LemonadeAllocation(),
        new LemonadePayment(), 
        new LemonadeQuery(),
        new OneShotActivity(),
        new LemonadeAnonymous(), 
        new OneShotTermination(),
        new ThreeRoundTermination());
  }
}
