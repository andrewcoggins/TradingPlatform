package brown.auction.preset;

import brown.auction.rules.library.LemonadeActivity;
import brown.auction.rules.library.LemonadeGroupedPayment;
import brown.auction.rules.library.LemonadeNonAnonymous;
import brown.auction.rules.library.LemonadeQuery;
import brown.auction.rules.library.NoAllocation;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneGrouping;
import brown.auction.rules.library.OneShotTermination;

/**
 * Lemonade with no grouping, no anonymity.
 * @author acoggins
 *
 */
public class LemonadeNonAnonRules extends AbsMarketRules {
  private int numSlots;
  
  public LemonadeNonAnonRules(int numSlots) {
    super(new NoAllocation(),
        new LemonadeGroupedPayment(numSlots), 
        new LemonadeQuery(),
        new OneGrouping(),        
        new LemonadeActivity(numSlots),
        new LemonadeNonAnonymous(numSlots), 
        new OneShotTermination(),
        new NoRecordKeeping());
    this.numSlots = numSlots;   
  }

  @Override
  public AbsMarketRules copy() {
    return new LemonadeNonAnonRules(this.numSlots);
  } 
}
