package brown.rules.library;

import java.util.Map;

import brown.market.marketstate.IMarketState;
import brown.rules.IRecordKeepingRule;
import brown.value.valuation.IValuation;

public class NoRecordKeeping implements IRecordKeepingRule{

  @Override
  public void record(IMarketState state, Map<Integer, IValuation> privateVals) {
  }
  
}
