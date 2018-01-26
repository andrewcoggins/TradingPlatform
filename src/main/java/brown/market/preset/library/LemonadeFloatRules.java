package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.LemonadeAnonymous;
import brown.rules.library.LemonadePaymentFloat;
import brown.rules.library.LemonadeQuery;
import brown.rules.library.LemonadeActivity;
import brown.rules.library.GroupsThreeFourFiveAlloc;
import brown.rules.library.OneShotTermination;
import brown.rules.library.XRoundTermination;

public class LemonadeFloatRules extends AbsMarketPreset {
  
  /**
   * some of these are guesses.
   * need to pass in the market internal state, 
   * or otherwise delete it from this constructor.
   */
  public LemonadeFloatRules(int numSlots,int numRuns) {
    super(new GroupsThreeFourFiveAlloc(),
        new LemonadePaymentFloat(numSlots), 
        new LemonadeQuery(),
        new LemonadeActivity(numSlots),
        new LemonadeAnonymous(numSlots), 
        new OneShotTermination(),
        new XRoundTermination(numRuns));
  } 
}
