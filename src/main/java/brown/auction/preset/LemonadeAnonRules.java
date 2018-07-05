package brown.auction.preset;

import brown.auction.rules.library.LemonadeActivity;
import brown.auction.rules.library.LemonadeAnonymous;
import brown.auction.rules.library.LemonadeGroupedPayment;
import brown.auction.rules.library.LemonadeQuery;
import brown.auction.rules.library.NoAllocation;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneGrouping;
import brown.auction.rules.library.OneShotTermination;
import brown.auction.rules.library.XRoundTermination;

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
