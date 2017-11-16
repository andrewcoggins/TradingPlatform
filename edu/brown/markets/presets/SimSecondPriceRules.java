package brown.markets.presets;

import brown.rules.activityrules.library.OneShotActivity;
import brown.rules.allocationrules.library.SimpleHighestBidderAllocation;
import brown.rules.irpolicies.library.AnonymousPolicy;
import brown.rules.paymentrules.library.SimpleSecondPrice;
import brown.rules.queryrules.library.SealedBidQuery;
import brown.rules.terminationconditions.library.OneShotTermination;

public class SimSecondPriceRules extends MarketPreset {

  /**
   * some of these are guesses.
   * need to pass in the market internal state, or otherwise delete it from this constructor.
   */
  public SimSecondPriceRules() {
    this.aRule = new SimpleHighestBidderAllocation(); 
    this.pRule = new SimpleSecondPrice(); 
    this.qRule = new SealedBidQuery();
    this.actRule = new OneShotActivity();
    this.infoPolicy = new AnonymousPolicy();
    this.tCondition = new OneShotTermination();
  }

}