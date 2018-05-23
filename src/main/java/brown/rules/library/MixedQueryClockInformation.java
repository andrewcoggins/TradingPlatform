package brown.rules.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.bid.library.QueryBid;
import brown.logging.Logging;
import brown.market.library.SpecValInfo;
import brown.market.marketstate.IMarketState;
import brown.messages.library.CombinatorialClockReport;
import brown.messages.library.GameReportMessage;
import brown.messages.library.SpecValValuationReport;
import brown.messages.library.TradeMessage;
import brown.rules.IInformationRevelationPolicy;
import brown.tradeable.ITradeable;

public class MixedQueryClockInformation implements IInformationRevelationPolicy {


  @Override
  public void setReport(IMarketState state) {
    if (state.getTicks() %2 == 1){
      // Do Query report
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
    } else {
      // Do CC Report
      Map<Integer,List<GameReportMessage>> reports = new HashMap<Integer,List<GameReportMessage>>();        

      Map<ITradeable, List<Integer>> altAlloc = state.getAltAlloc();
      Map<Integer, List<ITradeable>> alloc = state.getAllocation();
      
      for (Integer agent : alloc.keySet()){
        Map<ITradeable, Double > report = new HashMap<ITradeable,Double>();
        for (ITradeable t: state.getTradeables()){
          if (alloc.get(agent).contains(t)){
            report.put(t,(Double) 1./altAlloc.get(t).size());
          } else {
            report.put(t,0.);
          }
        }
        List<GameReportMessage> currList = reports.getOrDefault(agent, new LinkedList<GameReportMessage>());
        currList.add(new CombinatorialClockReport(report));
        reports.put(agent, currList);
        state.setReport(reports);              
      }      
    }
  }

  @Override
  public void reset() {
  }

  @Override
  public void constructSummaryState(IMarketState state) {
  }

}
