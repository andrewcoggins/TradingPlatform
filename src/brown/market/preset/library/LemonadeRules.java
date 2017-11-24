package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.activityrules.library.OneShotActivity;
import brown.rules.allocationrules.library.LemonadeAllocation;
import brown.rules.irpolicies.library.AnonymousPolicy;
import brown.rules.paymentrules.library.LemonadePayment;
import brown.rules.queryrules.library.LemonadeQuery;
import brown.rules.terminationconditions.library.OneShotTermination;
import brown.rules.terminationconditions.library.XRoundTermination;

public class LemonadeRules extends AbsMarketPreset {

  /**
   * some of these are guesses.
   * need to pass in the market internal state, or otherwise delete it from this constructor.
   */
  public LemonadeRules() {
    this.aRule = new LemonadeAllocation(); 
    this.pRule = new LemonadePayment(); 
    this.qRule = new LemonadeQuery();
    this.actRule = new OneShotActivity();
    this.infoPolicy = new AnonymousPolicy();
    this.innerTCondition = new OneShotTermination();
    this.outerTCondition = new XRoundTermination();
  }

}