package brown.rules.library;

import java.util.HashMap;
import java.util.Map;

import brown.market.marketstate.IMarketState;
import brown.messages.library.GameReportMessage;
import brown.rules.IInformationRevelationPolicy;

public class SSSPAnonymous implements IInformationRevelationPolicy {

  @Override
  public void handleInfo() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReport(IMarketState state) {
    Map<Integer,GameReportMessage> reports = new HashMap<Integer,GameReportMessage>();    
    state.setReport(reports);
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void constructSummaryState(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

}
