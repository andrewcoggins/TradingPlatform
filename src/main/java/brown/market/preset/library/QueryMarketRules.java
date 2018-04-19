package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.NoAllocation;
import brown.rules.library.NoPayment;
import brown.rules.library.NoRecordKeeping;
import brown.rules.library.OneGrouping;
import brown.rules.library.OneShotTermination;
import brown.rules.library.QueryRoundActivity;
import brown.rules.library.QueryRoundInformation;
import brown.rules.library.QueryRoundQuery;
import brown.rules.library.XRoundTermination;

public class QueryMarketRules extends AbsMarketPreset {
  int nrounds;
  
  /**
   * some of these are guesses.
   * need to pass in the market internal state, 
   * or otherwise delete it from this constructor.
   */
  public QueryMarketRules(int nrounds) {
    super(new NoAllocation(),
        new NoPayment(),
        new QueryRoundQuery(), 
        new OneGrouping(),
        new QueryRoundActivity(),
        new QueryRoundInformation(),
        new OneShotTermination(), 
        new XRoundTermination(nrounds),
        new NoRecordKeeping());
    this.nrounds = nrounds;
  }

  @Override
  public AbsMarketPreset copy() {
    return new QueryMarketRules(this.nrounds);
  }
}
