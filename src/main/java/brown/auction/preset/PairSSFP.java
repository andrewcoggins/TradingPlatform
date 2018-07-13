package brown.auction.preset;

import brown.auction.rules.library.HighestPriceAllocation;
import brown.auction.rules.library.OneShotActivity;
import brown.auction.rules.library.OneShotTermination;
import brown.auction.rules.library.PairGrouping;
import brown.auction.rules.library.RecordBids;
import brown.auction.rules.library.SSSPAnonymousPolicy;
import brown.auction.rules.library.SimpleFirstPricePayment;
import brown.auction.rules.library.SimpleQuery;

/**
 * Simple Simultaneous first price auction with 
 * pair grouping.
 * @author acoggins
 *
 */
public class PairSSFP extends AbsMarketPreset {
  private String filePath;
  
  public PairSSFP(String filePath) {
    super(new HighestPriceAllocation(),
        new SimpleFirstPricePayment(),
        new SimpleQuery(), 
        new PairGrouping(),
        new OneShotActivity(),
        new SSSPAnonymousPolicy(),
        new OneShotTermination(), 
        new RecordBids(filePath));
    this.filePath = filePath;
  }

  @Override
  public AbsMarketPreset copy() {
    return new PairSSFP(this.filePath);
  }
  
  
}
