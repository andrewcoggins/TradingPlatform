package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.OneGrouping;
import brown.rules.library.OneShotTermination;
import brown.rules.library.RecordBids;
import brown.rules.library.SimpleFirstPricePayment;
import brown.rules.library.OneShotActivity;
import brown.rules.library.SSSPAnonymous;
import brown.rules.library.SimpleQuery;
import brown.rules.library.HighestPriceAllocation;
import brown.rules.library.XRoundTermination;

public class NormalSSFP extends AbsMarketPreset {
  private int numRuns;
  private String filePath;

  /**
   * some of these are guesses.
   * need to pass in the market internal state, 
   * or otherwise delete it from this constructor.
   */
  public NormalSSFP(int numRuns, String filePath) {
    super(new HighestPriceAllocation(),
        new SimpleFirstPricePayment(),
        new SimpleQuery(), 
        new OneGrouping(),
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
    return new NormalSSFP(this.numRuns, this.filePath);
  }
  
}
