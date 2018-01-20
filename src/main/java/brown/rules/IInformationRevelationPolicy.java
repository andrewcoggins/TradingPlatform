package brown.rules;

import brown.market.marketstate.IMarketState;

public interface IInformationRevelationPolicy {
  
  public void handleInfo();

  public void setReport(IMarketState state);

  public void reset();

  public void constructSummaryState(IMarketState state);
}
