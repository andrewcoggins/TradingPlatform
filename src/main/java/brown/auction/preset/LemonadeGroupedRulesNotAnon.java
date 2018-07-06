package brown.auction.preset;

import brown.auction.rules.library.LemonadeActivity;
import brown.auction.rules.library.LemonadeGroupedPayment;
import brown.auction.rules.library.LemonadeNonAnonymous;
import brown.auction.rules.library.LemonadeQuery;
import brown.auction.rules.library.NoAllocation;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneShotTermination;
import brown.auction.rules.library.ThreeFourFiveGrouping;

/**
 * Lemonade Rules with groupings, but bidding is not anonymous.
 * @author acoggins
 *
 */
public class LemonadeGroupedRulesNotAnon extends AbsMarketPreset {
  private int numSlots;
  private int numRuns;

  public LemonadeGroupedRulesNotAnon(int numSlots) {
    super(new NoAllocation(),
        new LemonadeGroupedPayment(numSlots), 
        new LemonadeQuery(),
        new ThreeFourFiveGrouping(),        
        new LemonadeActivity(numSlots),
        new LemonadeNonAnonymous(numSlots), 
        new OneShotTermination(),
        new NoRecordKeeping());
    this.numSlots = numSlots;       
  }

  @Override
  public AbsMarketPreset copy() {
    return new LemonadeGroupedRulesNotAnon(this.numSlots);
  } 
}
