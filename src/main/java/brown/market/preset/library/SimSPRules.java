package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.LemonadeAnonymous;
import brown.rules.library.OneShotActivity;
import brown.rules.library.OneShotTermination;
import brown.rules.library.SealedBidQuery;
import brown.rules.library.SimpleHighestBidderAllocation;
import brown.rules.library.SimpleSecondPrice;
import brown.rules.library.XRoundTermination;

public class SimSPRules extends AbsMarketPreset {

  /**
   * some of these are guesses.
   * need to pass in the market internal state, 
   * or otherwise delete it from this constructor.
   */
  public SimSPRules() {
    super(new SimpleHighestBidderAllocation(),
        new SimpleSecondPrice(),
        new SealedBidQuery(), 
        new OneShotActivity(),
        new LemonadeAnonymous(),
        new OneShotTermination(), 
        new XRoundTermination());
  }
  
}
