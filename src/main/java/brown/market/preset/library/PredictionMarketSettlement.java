package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.CallMarketQuery;
import brown.rules.library.NoAllocation;
import brown.rules.library.NoBiddingActivity;
import brown.rules.library.NoBiddingTermination;
import brown.rules.library.NoRecordKeeping;
import brown.rules.library.OneGrouping;
import brown.rules.library.PredictionMarketInformation;
import brown.rules.library.PredictionMarketPayment;
import brown.rules.library.XRoundTermination;

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
