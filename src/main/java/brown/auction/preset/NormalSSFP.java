package brown.auction.preset;

import brown.auction.rules.library.HighestPriceAllocation;
import brown.auction.rules.library.OneGrouping;
import brown.auction.rules.library.OneShotActivity;
import brown.auction.rules.library.OneShotTermination;
import brown.auction.rules.library.RecordBids;
import brown.auction.rules.library.SSSPAnonymousPolicy;
import brown.auction.rules.library.SimpleFirstPricePayment;
import brown.auction.rules.library.SimpleQuery;

/**
 * Simple Simultaneous first price auction. 
 * @author acoggins
 *
 */
public class NormalSSFP extends AbsMarketPreset {
  private String filePath;

  public NormalSSFP(String filePath) {
    super(new HighestPriceAllocation(),
        new SimpleFirstPricePayment(),
        new SimpleQuery(), 
        new OneGrouping(),
        new OneShotActivity(),
        new SSSPAnonymousPolicy(),
        new OneShotTermination(), 
        new RecordBids(filePath));
    this.filePath = filePath;    
  }

  @Override
  public AbsMarketPreset copy() {
    return new NormalSSFP(this.filePath);
  }
  
}
