package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.activityrules.library.OneShotActivity;
import brown.rules.allocationrules.library.SimpleHighestBidderAllocation;
import brown.rules.irpolicies.library.LemonadeAnonymous;
import brown.rules.paymentrules.library.SimpleSecondPrice;
import brown.rules.queryrules.library.SealedBidQuery;
import brown.rules.terminationconditions.library.OneShotTermination;
import brown.rules.terminationconditions.library.ThreeRoundTermination;

public class SSSPRules extends AbsMarketPreset {

  /**
   * some of these are guesses.
   * need to pass in the market internal state, or otherwise delete it from this constructor.
   */
  public SSSPRules() {
    this.aRule = new SimpleHighestBidderAllocation(); 
    this.pRule = new SimpleSecondPrice(); 
    this.qRule = new SealedBidQuery();
    this.actRule = new OneShotActivity();
    this.infoPolicy = new LemonadeAnonymous();
    this.innerTCondition = new OneShotTermination();
    this.outerTCondition = new ThreeRoundTermination();
  }
}