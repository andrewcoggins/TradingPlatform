package brown.rules;

import java.io.IOException;
import java.util.Map;

import brown.market.marketstate.IMarketState;
import brown.value.valuation.IValuation;

public interface IRecordKeepingRule {

  void record(IMarketState state, Map<Integer, IValuation> privateVals) throws IOException;
  
}
