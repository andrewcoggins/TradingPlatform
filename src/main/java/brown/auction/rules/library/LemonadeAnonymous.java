package brown.auction.rules.library; 

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.mechanism.bidbundle.library.BundleType;
import brown.mechanism.bidbundle.library.GameBidBundle;
import brown.platform.messages.library.GameReportMessage;
import brown.platform.messages.library.LemonadeReportMessage;
import brown.platform.messages.library.TradeMessage;

public class LemonadeAnonymous implements IInformationRevelationPolicy{
  private int numSlots;
  
  public LemonadeAnonymous(int numSlots) {
    this.numSlots = numSlots;
  }


  @Override
  public void setReport(IMarketState state) {
    Map<Integer,List<GameReportMessage>> reports = new HashMap<Integer,List<GameReportMessage>>();    
    
    for (List<Integer> group : state.getGroups()){
      Integer[] report = new Integer[numSlots];    
      for (int i = 0; i<numSlots;i++){
        report[i] = 0;
      }        
      for (TradeMessage b:state.getBids()){
        // Must be right type and within relevent group
        if (b.Bundle.getType() != BundleType.GAME | !group.contains(b.AgentID))
          continue;
        GameBidBundle lemonadeBid = (GameBidBundle) b.Bundle;
        int index = lemonadeBid.getBids().move;
        report[index] = report[index] + 1;
      }
      for (Integer agent : group){
        List<GameReportMessage> reportList = new LinkedList<GameReportMessage>();
        reportList.add(new LemonadeReportMessage(report,true));
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