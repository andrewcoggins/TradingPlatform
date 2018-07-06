package brown.auction.rules.library; 

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.prevstate.library.PredictionMarketInfo;
import brown.auction.prevstate.library.PrevStateInfo;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.platform.accounting.library.Ledger;
import brown.platform.accounting.library.Order;
import brown.platform.accounting.library.Transaction;
import brown.platform.messages.library.CallMarketReportMessage;
import brown.platform.messages.library.GameReportMessage;

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