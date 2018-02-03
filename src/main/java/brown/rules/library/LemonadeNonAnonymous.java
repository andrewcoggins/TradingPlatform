package brown.rules.library; 

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.bidbundle.BundleType;
import brown.bidbundle.library.GameBidBundle;
import brown.logging.Logging;
import brown.market.marketstate.IMarketState;
import brown.messages.library.GameReportMessage;
import brown.messages.library.LemonadeReportMessage;
import brown.messages.library.TradeMessage;
import brown.rules.IInformationRevelationPolicy;

public class LemonadeNonAnonymous implements IInformationRevelationPolicy{
  private int numSlots;
  
  public LemonadeNonAnonymous(int numSlots) {
    this.numSlots = numSlots;
  }

  @Override
  public void handleInfo() {
  }

  @SuppressWarnings("unchecked")
  @Override
  public void setReport(IMarketState state) {
    Map<Integer,GameReportMessage> reports = new HashMap<Integer,GameReportMessage>();    
    
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
        reports.put(agent,new LemonadeReportMessage(report_id,report_count,false));
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