package brown.auction.rules.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.prevstate.library.BlankStateInfo;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.platform.messages.library.GameReportMessage;
import brown.platform.messages.library.SimpleSealedReportMessage;

/**
 * Information revelation policy for SSSP auction.
 * Constructs game report messages detailing the winners 
 * of the auctions.
 * @author acoggins
 *
 */
public class SSSPAnonymous implements IInformationRevelationPolicy {

  @Override
  public void setReport(IMarketState state) {
    Map<Integer,List<GameReportMessage>> reports = new HashMap<Integer,List<GameReportMessage>>();    
    
    for (List<Integer> group : state.getGroups()) {      
      int winner = group.get(0);
      // find the winner
      for (Integer agent : state.getAllocation().keySet()) {
        if (group.contains(agent)) {
          winner = agent;
        }
      }
      // report is winner for this group and the size of group
      for (Integer agent : group) {
        List<GameReportMessage> reportList = new LinkedList<GameReportMessage>();
        reportList.add(new SimpleSealedReportMessage(winner,group.size()));
        reports.put(agent,reportList);
      }
    }
    state.setReport(reports);
  }

  @Override
  public void reset() {
    
  }

  @Override
  public void constructSummaryState(IMarketState state) {
    state.setSummaryState(new BlankStateInfo());
  }

}