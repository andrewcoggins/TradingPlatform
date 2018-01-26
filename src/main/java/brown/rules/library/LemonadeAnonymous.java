package brown.rules.library; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.bid.bidbundle.BundleType;
import brown.bid.bidbundle.library.GameBidBundle;
import brown.market.marketstate.IMarketState;
import brown.messages.library.GameReportMessage;
import brown.messages.library.LemonadeReportMessage;
import brown.messages.library.TradeMessage;
import brown.rules.IInformationRevelationPolicy;

public class LemonadeAnonymous implements IInformationRevelationPolicy{
  private int numSlots;
  
  public LemonadeAnonymous(int numSlots) {
    this.numSlots = numSlots;
  }

  @Override
  public void handleInfo() {
  }

  @Override
  public void setReport(IMarketState state) {
    Map<Integer,GameReportMessage> reports = new HashMap<Integer,GameReportMessage>();    
    
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
        reports.put(agent,new LemonadeReportMessage(report,true));
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