package brown.auction.preset;

import brown.auction.rules.library.LemonadeActivity;
import brown.auction.rules.library.LemonadeAnonymous;
import brown.auction.rules.library.LemonadeGroupedPayment;
import brown.auction.rules.library.LemonadeQuery;
import brown.auction.rules.library.NoAllocation;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneShotTermination;
import brown.auction.rules.library.ThreeFourFiveGrouping;
import brown.auction.rules.library.XRoundTermination;

/**
 * Lemonade game with groupings.
 * @author acoggins
 *
 */
public class LemonadeGroupedRulesAnon extends AbsMarketPreset {
  private int numSlots;
  private int numRuns;
  
  public LemonadeGroupedRulesAnon(int numSlots, int numRuns) {
    super(new NoAllocation(),
        new LemonadeGroupedPayment(numSlots), 
        new LemonadeQuery(),
        new ThreeFourFiveGrouping(),        
        new LemonadeActivity(numSlots),
        new LemonadeAnonymous(numSlots), 
        new OneShotTermination(),
        new XRoundTermination(numRuns),
        new NoRecordKeeping());
    this.numSlots = numSlots;
    this.numRuns = numRuns;          
  }

  @Override
  public AbsMarketPreset copy() {
    return new LemonadeGroupedRulesAnon(this.numSlots,this.numRuns);
  } 
  
  
}
