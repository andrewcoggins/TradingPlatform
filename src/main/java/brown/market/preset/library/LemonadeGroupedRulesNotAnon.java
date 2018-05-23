package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.LemonadeGroupedPayment;
import brown.rules.library.LemonadeNonAnonymous;
import brown.rules.library.LemonadeQuery;
import brown.rules.library.LemonadeActivity;
import brown.rules.library.NoAllocation;
import brown.rules.library.NoRecordKeeping;
import brown.rules.library.ThreeFourFiveGrouping;
import brown.rules.library.OneShotTermination;
import brown.rules.library.XRoundTermination;

/**
 * Lemonade Rules with groupings, but bidding is not anonymous.
 * @author acoggins
 *
 */
public class LemonadeGroupedRulesNotAnon extends AbsMarketPreset {
  private int numSlots;
  private int numRuns;

  public LemonadeGroupedRulesNotAnon(int numSlots, int numRuns) {
    super(new NoAllocation(),
        new LemonadeGroupedPayment(numSlots), 
        new LemonadeQuery(),
        new ThreeFourFiveGrouping(),        
        new LemonadeActivity(numSlots),
        new LemonadeNonAnonymous(numSlots), 
        new OneShotTermination(),
        new XRoundTermination(numRuns),
        new NoRecordKeeping());
    this.numSlots = numSlots;
    this.numRuns = numRuns;        
  }

  @Override
  public AbsMarketPreset copy() {
    return new LemonadeGroupedRulesNotAnon(this.numSlots,this.numRuns);
  } 
}
