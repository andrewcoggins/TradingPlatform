package brown.rules.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.bid.library.QueryBid;
import brown.bidbundle.library.QueryBundle;
import brown.market.library.SpecValInfo;
import brown.market.marketstate.IMarketState;
import brown.messages.library.CallMarketReportMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.SpecValValuationReport;
import brown.messages.library.TradeMessage;
import brown.rules.IInformationRevelationPolicy;

public class QueryRoundInformation implements IInformationRevelationPolicy {

  @Override
  public void handleInfo() {
    // TODO Auto-generated method stub

  }

  @Override
  public void setReport(IMarketState state) {
    Map<Integer,List<GameReportMessage>> reports = new HashMap<Integer,List<GameReportMessage>>();        
    List<TradeMessage> bids = state.getBids();
    SpecValInfo valInfo = (SpecValInfo) state.getPrevState();
    for (TradeMessage bid : bids){
      QueryBid qbid = (QueryBid) bid.Bundle.getBids();    
      
      List<GameReportMessage> currList = reports.getOrDefault(bid.AgentID, new LinkedList<GameReportMessage>());
      currList.add(new SpecValValuationReport(valInfo.queryValues(bid.AgentID, qbid.bundles)));      
      reports.put(bid.AgentID, currList);
      state.setReport(reports);      
    }
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub

  }

  @Override
  public void constructSummaryState(IMarketState state) {
    state.setSummaryState(state.getPrevState());
  }
}
