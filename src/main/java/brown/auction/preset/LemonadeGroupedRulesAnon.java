package brown.auction.preset;

import brown.auction.rules.library.LemonadeActivity;
import brown.auction.rules.library.LemonadeAnonymousPolicy;
import brown.auction.rules.library.LemonadeGroupedPayment;
import brown.auction.rules.library.LemonadeQuery;
import brown.auction.rules.library.NoAllocation;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneShotTermination;
import brown.auction.rules.library.ThreeFourFiveGrouping;

/**
 * Lemonade game with groupings.
 * @author acoggins
 *
 */
public class LemonadeGroupedRulesAnon extends AbsMarketPreset {
  private int numSlots;
  
  public LemonadeGroupedRulesAnon(int numSlots) {
    super(new NoAllocation(),
        new LemonadeGroupedPayment(numSlots), 
        new LemonadeQuery(),
        new ThreeFourFiveGrouping(),        
        new LemonadeActivity(numSlots),
        new LemonadeAnonymousPolicy(numSlots), 
        new OneShotTermination(),
        new NoRecordKeeping());
    this.numSlots = numSlots;         
  }

  @Override
  public AbsMarketPreset copy() {
    return new LemonadeGroupedRulesAnon(this.numSlots);
  } 
  
  
}
