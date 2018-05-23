package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.LemonadeGroupedPayment;
import brown.rules.library.LemonadeNonAnonymous;
import brown.rules.library.LemonadeQuery;
import brown.rules.library.LemonadeActivity;
import brown.rules.library.NoAllocation;
import brown.rules.library.NoRecordKeeping;
import brown.rules.library.OneGrouping;
import brown.rules.library.OneShotTermination;
import brown.rules.library.XRoundTermination;

/**
 * Lemonade with no grouping, no anonymity.
 * @author acoggins
 *
 */
public class LemonadeNonAnonRules extends AbsMarketPreset {
  private int numSlots;
  private int numRuns;
  
  public LemonadeNonAnonRules(int numSlots, int numRuns) {
    super(new NoAllocation(),
        new LemonadeGroupedPayment(numSlots), 
        new LemonadeQuery(),
        new OneGrouping(),        
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
    return new LemonadeNonAnonRules(this.numSlots, this.numRuns);
  } 
}
