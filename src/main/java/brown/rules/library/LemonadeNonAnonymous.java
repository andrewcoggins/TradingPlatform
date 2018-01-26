package brown.rules.library; 

import java.util.LinkedList;
import java.util.List;

import brown.bid.bidbundle.BundleType;
import brown.bid.bidbundle.library.GameBidBundle;
import brown.market.marketstate.IMarketState;
import brown.messages.library.LemonadeReportMessage;
import brown.messages.library.TradeMessage;
import brown.rules.IInformationRevelationPolicy;
import brown.setup.Logging;

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
    List<Integer>[] report_id = (List<Integer>[]) new List[numSlots];
    Integer[] report_count = new Integer[numSlots];    
    for (int i = 0; i<numSlots;i++){
      report_id[i] = new LinkedList<Integer>();
      report_count[i] = 0;
    }        
    
    List<TradeMessage> bids = state.getBids();
    for (TradeMessage b:bids){
      if (b.Bundle.getType() != BundleType.GAME)
        continue;
      GameBidBundle lemonadeBid = (GameBidBundle) b.Bundle;
      int index = lemonadeBid.getBids().move;
      Logging.log("AGENTID: "+ b.AgentID);
      report_id[index].add(b.AgentID);
      report_count[index] = report_count[index] + 1;
    }
    state.setReport(new LemonadeReportMessage(report_id,report_count,false));
  }

  @Override
  public void reset() {
  }

  @Override
  public void constructSummaryState(IMarketState state) {    
  }  
}