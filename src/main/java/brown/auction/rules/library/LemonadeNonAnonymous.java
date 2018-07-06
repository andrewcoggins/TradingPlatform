package brown.auction.rules.library; 

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.logging.library.Logging;
import brown.mechanism.bidbundle.library.BundleType;
import brown.mechanism.bidbundle.library.GameBidBundle;
import brown.platform.messages.library.GameReportMessage;
import brown.platform.messages.library.LemonadeReportMessage;
import brown.platform.messages.library.TradeMessage;

public class LemonadeNonAnonymous implements IInformationRevelationPolicy{
  private int numSlots;
  
  public LemonadeNonAnonymous(int numSlots) {
    this.numSlots = numSlots;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void setReport(IMarketState state) {
    Map<Integer,List<GameReportMessage>> reports = new HashMap<Integer,List<GameReportMessage>>();    
    
    for (List<Integer> group : state.getGroups()){
      List<Integer>[] report_id = (List<Integer>[]) new List[numSlots];
      Integer[] report_count = new Integer[numSlots];    
      for (int i = 0; i<numSlots;i++){
        report_id[i] = new LinkedList<Integer>();
        report_count[i] = 0;
      }        
      
      List<TradeMessage> bids = state.getBids();
      for (TradeMessage b:bids){
        if (b.Bundle.getType() != BundleType.GAME | !group.contains(b.AgentID))
          continue;
        GameBidBundle lemonadeBid = (GameBidBundle) b.Bundle;
        int index = lemonadeBid.getBids().move;
        report_id[index].add(b.AgentID);
        report_count[index] = report_count[index] + 1;
      }
      for (Integer agent : group){
        List<GameReportMessage> reportList = new LinkedList<GameReportMessage>();
        reportList.add(new LemonadeReportMessage(report_id,report_count,false));
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
  }  
}