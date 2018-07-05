package brown.auction.preset;

import brown.auction.rules.library.LemonadeActivity;
import brown.auction.rules.library.LemonadeGroupedPayment;
import brown.auction.rules.library.LemonadeNonAnonymous;
import brown.auction.rules.library.LemonadeQuery;
import brown.auction.rules.library.NoAllocation;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneShotTermination;
import brown.auction.rules.library.ThreeFourFiveGrouping;
import brown.auction.rules.library.XRoundTermination;

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
