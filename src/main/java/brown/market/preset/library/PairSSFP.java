package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.OneShotTermination;
import brown.rules.library.PairGrouping;
import brown.rules.library.RecordBids;
import brown.rules.library.SimpleFirstPricePayment;
import brown.rules.library.OneShotActivity;
import brown.rules.library.SSSPAnonymous;
import brown.rules.library.SimpleQuery;
import brown.rules.library.HighestPriceAllocation;
import brown.rules.library.XRoundTermination;

/**
 * Simple Simultaneous first price auction with 
 * pair grouping.
 * @author acoggins
 *
 */
public class PairSSFP extends AbsMarketPreset {
  private int numRuns;
  private String filePath;
  
  public PairSSFP(int numRuns, String filePath) {
    super(new HighestPriceAllocation(),
        new SimpleFirstPricePayment(),
        new SimpleQuery(), 
        new PairGrouping(),
        new OneShotActivity(),
        new SSSPAnonymous(),
        new OneShotTermination(), 
        new XRoundTermination(numRuns),
        new RecordBids(filePath));
    this.numRuns = numRuns;
    this.filePath = filePath;
  }

  @Override
  public AbsMarketPreset copy() {
    return new PairSSFP(this.numRuns, this.filePath);
  }
  
  
}
