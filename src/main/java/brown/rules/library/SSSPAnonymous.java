package brown.rules.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.market.library.BlankStateInfo;
import brown.market.marketstate.IMarketState;
import brown.messages.library.GameReportMessage;
import brown.messages.library.SimpleSealedReportMessage;
import brown.rules.IInformationRevelationPolicy;

public class SSSPAnonymous implements IInformationRevelationPolicy {

  @Override
  public void handleInfo() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReport(IMarketState state) {
    Map<Integer,List<GameReportMessage>> reports = new HashMap<Integer,List<GameReportMessage>>();    
    
    for (List<Integer> group : state.getGroups()){      
      int winner = group.get(0);
      // find the winner
      for (Integer agent : state.getAllocation().keySet()){
        if (group.contains(agent)){
          winner = agent;
        }
      }
      // report is winner for this group and the size of group
      for (Integer agent : group){
        List<GameReportMessage> reportList = new LinkedList<GameReportMessage>();
        reportList.add(new SimpleSealedReportMessage(winner,group.size()));
        reports.put(agent,reportList);
      }
    }
    state.setReport(reports);
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void constructSummaryState(IMarketState state) {
    // TODO Auto-generated method stub
    state.setSummaryState(new BlankStateInfo());
  }

}
