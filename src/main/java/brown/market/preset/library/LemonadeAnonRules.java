package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.LemonadeAnonymous;
import brown.rules.library.LemonadeGroupedPayment;
import brown.rules.library.LemonadeQuery;
import brown.rules.library.LemonadeActivity;
import brown.rules.library.NoAllocation;
import brown.rules.library.NoRecordKeeping;
import brown.rules.library.OneGrouping;
import brown.rules.library.OneShotTermination;
import brown.rules.library.XRoundTermination;

/**
 * Lemonade game where bids are anonymous.
 * @author acoggins
 *
 */
public class LemonadeAnonRules extends AbsMarketPreset {
  private int numSlots;
  private int numRuns;
  
  public LemonadeAnonRules(int numSlots, int numRuns) {
    super(new NoAllocation(),
        new LemonadeGroupedPayment(numSlots), 
        new LemonadeQuery(),
        new OneGrouping(),        
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
    return new LemonadeAnonRules(this.numSlots,this.numRuns);
  } 
}
