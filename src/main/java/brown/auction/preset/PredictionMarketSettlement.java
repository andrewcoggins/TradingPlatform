package brown.auction.preset;

import brown.auction.rules.library.CallMarketQuery;
import brown.auction.rules.library.NoAllocation;
import brown.auction.rules.library.NoBiddingActivity;
import brown.auction.rules.library.NoBiddingTermination;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneGrouping;
import brown.auction.rules.library.PredictionMarketInformation;
import brown.auction.rules.library.PredictionMarketPayment;
import brown.auction.rules.library.XRoundTermination;

/**
 * Prediction market with a settlement round.
 * @author acoggins
 *
 */
public class PredictionMarketSettlement extends AbsMarketPreset {

  public PredictionMarketSettlement() {
    super(new NoAllocation(),
        new PredictionMarketPayment(),
        new CallMarketQuery(), 
        new OneGrouping(),
        new NoBiddingActivity(),
        new PredictionMarketInformation(),
        new NoBiddingTermination(), 
        new XRoundTermination(1),
        new NoRecordKeeping());
  }

  @Override
  public AbsMarketPreset copy() {
    return new PredictionMarketSettlement();
  }
}
