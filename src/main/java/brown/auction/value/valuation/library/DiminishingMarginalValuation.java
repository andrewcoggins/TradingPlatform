package brown.auction.value.valuation.library;

import brown.auction.value.valuation.IValuation;
import brown.logging.library.ErrorLogging;

public class DiminishingMarginalValuation extends AbsSizeDependentValuation implements IValuation {

  public DiminishingMarginalValuation(double baseValue, double factor) {
      super(baseValue, factor);
      if (factor > 1) {
        ErrorLogging.log("ERROR: diminishing marginal valuation factor should be less than 1");
      }
  }
}
