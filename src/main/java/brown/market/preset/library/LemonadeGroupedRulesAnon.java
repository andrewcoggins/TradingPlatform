package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.BlankAllocation;
import brown.rules.library.LemonadeAnonymous;
import brown.rules.library.LemonadeGroupedPayment;
import brown.rules.library.LemonadeQuery;
import brown.rules.library.LemonadeActivity;
import brown.rules.library.ThreeFourFiveGrouping;
import brown.rules.library.OneShotTermination;
import brown.rules.library.XRoundTermination;

public class LemonadeGroupedRulesAnon extends AbsMarketPreset {
  
  /**
   * some of these are guesses.
   * need to pass in the market internal state, 
   * or otherwise delete it from this constructor.
   */
  public LemonadeGroupedRulesAnon(int numSlots, int numRuns) {
    super(new BlankAllocation(),
        new LemonadeGroupedPayment(numSlots), 
        new LemonadeQuery(),
        new ThreeFourFiveGrouping(),        
        new LemonadeActivity(numSlots),
        new LemonadeAnonymous(numSlots), 
        new OneShotTermination(),
        new XRoundTermination(numRuns));
  } 
}
