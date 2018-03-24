package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.BlankAllocation;
import brown.rules.library.CallMarketQuery;
import brown.rules.library.NoBiddingActivity;
import brown.rules.library.NoBiddingTermination;
import brown.rules.library.NoRecordKeeping;
import brown.rules.library.OneGrouping;
import brown.rules.library.PredictionMarketInformation;
import brown.rules.library.PredictionMarketPayment;
import brown.rules.library.XRoundTermination;

public class PredictionMarketSettlement extends AbsMarketPreset {
  /**
   * some of these are guesses.
   * need to pass in the market internal state, 
   * or otherwise delete it from this constructor.
   */
  public PredictionMarketSettlement() {
    super(new BlankAllocation(),
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
