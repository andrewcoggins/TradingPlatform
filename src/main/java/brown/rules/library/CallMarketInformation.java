package brown.rules.library; 

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.accounting.library.Ledger;
import brown.accounting.library.Transaction;
import brown.market.library.PredictionMarketInfo;
import brown.market.library.PrevStateInfo;
import brown.market.marketstate.IMarketState;
import brown.market.marketstate.library.Order;
import brown.messages.library.CallMarketReportMessage;
import brown.messages.library.GameReportMessage;
import brown.rules.IInformationRevelationPolicy;

/**
 * IR policy for a two-sided call market.
 * @author kerry
 *
 */
public class CallMarketInformation implements IInformationRevelationPolicy {
  private Ledger ledger;
  private int limit = 100;
  
  public CallMarketInformation() {
    this.ledger = new Ledger();
  }

  @Override
  public void setReport(IMarketState state) {
    List<Order> orders = state.getPayments();
    Map<Integer,List<GameReportMessage>> reports = new HashMap<Integer,List<GameReportMessage>>();    
    for (Order order: orders) {
      Transaction trans = order.toTransaction();
      this.ledger.add(trans);            
    }       
    List<Ledger> ledgerList = this.ledger.splitUnshared(limit);
    for (Ledger l: ledgerList) {
      for (List<Integer> group : state.getGroups()){
        for (Integer agent : group) {   
          List<GameReportMessage> currList = reports.getOrDefault(agent, new LinkedList<GameReportMessage>());
          currList.add(new CallMarketReportMessage(l.getSanitizedUnshared(agent)));
          reports.put(agent, currList);
        }        
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