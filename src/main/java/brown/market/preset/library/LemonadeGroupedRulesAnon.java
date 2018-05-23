package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.LemonadeAnonymous;
import brown.rules.library.LemonadeGroupedPayment;
import brown.rules.library.LemonadeQuery;
import brown.rules.library.LemonadeActivity;
import brown.rules.library.NoAllocation;
import brown.rules.library.NoRecordKeeping;
import brown.rules.library.ThreeFourFiveGrouping;
import brown.rules.library.OneShotTermination;
import brown.rules.library.XRoundTermination;

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
