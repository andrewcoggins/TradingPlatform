package brown.rules.library; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.accounting.library.Ledger;
import brown.accounting.library.Transaction;
import brown.logging.Logging;
import brown.market.library.PredictionMarketInfo;
import brown.market.library.PrevStateInfo;
import brown.market.marketstate.IMarketState;
import brown.market.marketstate.library.Order;
import brown.messages.library.CallMarketReportMessage;
import brown.messages.library.GameReportMessage;
import brown.rules.IInformationRevelationPolicy;

public class CallMarketInformation implements IInformationRevelationPolicy{
  private Ledger ledger;
  
  public CallMarketInformation() {
    this.ledger = new Ledger();
  }

  @Override
  public void handleInfo() {
  }

  @Override
  public void setReport(IMarketState state) {
    List<Order> orders = state.getPayments();
    Map<Integer,GameReportMessage> reports = new HashMap<Integer,GameReportMessage>();    
    for (Order order: orders){
      Transaction trans = order.toTransaction();
      this.ledger.add(trans);            
    }      
    for (List<Integer> group : state.getGroups()){
      for (Integer agent : group){      
        reports.put(agent, new CallMarketReportMessage(this.ledger.getSanitizedUnshared(agent)));        
      }
    }    
    this.ledger.clearUnshared();
    state.setReport(reports);
  }

  @Override
  public void reset() {
  }

  @Override
  public void constructSummaryState(IMarketState state) {        
    PrevStateInfo summary = new PredictionMarketInfo(this.ledger.getList());    
    state.setSummaryState(summary);
  }  
}