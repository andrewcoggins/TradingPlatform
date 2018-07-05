package brown.auction.rules.library;

import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IRecordKeepingRule;
import brown.auction.value.valuation.IValuation;

public class NoRecordKeeping implements IRecordKeepingRule{

  @Override
  public void record(IMarketState state, Map<Integer, IValuation> privateVals) {
  }
  
}
