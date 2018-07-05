package brown.auction.preset;

import brown.auction.rules.library.HighestPriceAllocation;
import brown.auction.rules.library.OneGrouping;
import brown.auction.rules.library.OneShotActivity;
import brown.auction.rules.library.OneShotTermination;
import brown.auction.rules.library.RecordBids;
import brown.auction.rules.library.SSSPAnonymous;
import brown.auction.rules.library.SimpleFirstPricePayment;
import brown.auction.rules.library.SimpleQuery;
import brown.auction.rules.library.XRoundTermination;

/**
 * Simple Simultaneous first price auction. 
 * @author acoggins
 *
 */
public class NormalSSFP extends AbsMarketPreset {
  private int numRuns;
  private String filePath;

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
