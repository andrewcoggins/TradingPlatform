package brown.auction.rules.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.messages.library.CombinatorialClockReport;
import brown.platform.messages.library.GameReportMessage;

/**
 * IR policy for a clock auction. Produces a series of combinatorial clock
 * reports to go back to agents.
 * @author kerry
 *
 */
public class ClockInformation implements IInformationRevelationPolicy {


  @Override
  public void setReport(IMarketState state) {
    // Do CC Report
    Map<Integer,List<GameReportMessage>> reports = new HashMap<Integer,List<GameReportMessage>>();        

    Map<ITradeable, List<Integer>> altAlloc = state.getAltAlloc();
    Map<Integer, List<ITradeable>> alloc = state.getAllocation();
    
    for (Integer agent : alloc.keySet()){
      Map<ITradeable, Double> report = new HashMap<ITradeable,Double>();
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

  @Override
  public void reset() {
    // noop
  }

  @Override
  public void constructSummaryState(IMarketState state) {
    state.setSummaryState(state.getPrevState());
  }
}
