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

    super(new ComplexHighestBidderAllocation(),
        new ComFirstPricePayment(),
        new SealedBidQuery(), 
        new OneShotActivity(),
        new LemonadeAnonymous(),
        new OneShotTermination(),
        new OneRoundTermination());
  }
}
