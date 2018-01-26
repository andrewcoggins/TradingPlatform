package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.ComFirstPricePayment;
import brown.rules.library.ComplexHighestBidderAllocation;
import brown.rules.library.LemonadeAnonymous;
import brown.rules.library.OneShotActivity;
import brown.rules.library.OneShotTermination;
import brown.rules.library.SSSPQuery;
import brown.rules.library.XRoundTermination;


public class CombinatorialRules extends AbsMarketPreset {
  
  public CombinatorialRules() {
    super(new ComplexHighestBidderAllocation(),
        new ComFirstPricePayment(),
        new SSSPQuery(), 
        new OneShotActivity(),
        new LemonadeAnonymous(),
        new OneShotTermination(),
        new XRoundTermination());
  }
  
}
