package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.activityrules.library.OneShotActivity;
import brown.rules.allocationrules.library.ComplexHighestBidderAllocation;
import brown.rules.irpolicies.library.LemonadeAnonymous;
import brown.rules.paymentrules.library.ComFirstPricePayment;
import brown.rules.queryrules.library.SealedBidQuery;
import brown.rules.terminationconditions.library.OneRoundTermination;
import brown.rules.terminationconditions.library.OneShotTermination;

public class CombinatorialRules extends AbsMarketPreset {
  
  public CombinatorialRules() {
    this.aRule = new ComplexHighestBidderAllocation(); 
    this.pRule = new ComFirstPricePayment(); 
    this.qRule = new SealedBidQuery();
    this.actRule = new OneShotActivity();
    this.infoPolicy = new LemonadeAnonymous();
    this.innerTCondition = new OneShotTermination();
    this.outerTCondition = new OneRoundTermination();
  }
}