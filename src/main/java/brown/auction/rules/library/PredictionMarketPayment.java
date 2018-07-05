package brown.auction.rules.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.prevstate.PredictionMarketInfo;
import brown.auction.prevstate.PrevStateInfo;
import brown.auction.prevstate.PrevStateType;
import brown.auction.rules.IPaymentRule;
import brown.logging.Logging;
import brown.platform.accounting.Order;
import brown.platform.accounting.Transaction;

public class PredictionMarketPayment implements IPaymentRule {

  @Override
  public void setOrders(IMarketState state) {
    PrevStateInfo pstate = state.getPrevState();
    if (pstate.getType() != PrevStateType.PREDICTION){
      Logging.log("WRONG PREV STATE INFO CONFIG");
    } else {
      List<Order> orders = new LinkedList<Order>();      
      PredictionMarketInfo info = (PredictionMarketInfo) pstate;
      
      List<Transaction> trades = info.tradeHistory;
      Boolean coin = info.trueCoin;
      
      Map<Integer,Integer> AgentToExposure = new HashMap<Integer,Integer>();
      for (Transaction trade: trades){
        Integer seller = trade.FROM;
        Integer buyer = trade.TO;
        
        if (AgentToExposure.containsKey(seller)){
          AgentToExposure.put(seller, (int) (AgentToExposure.get(seller)-trade.QUANTITY));          
        } else {
          AgentToExposure.put(seller,(int) (-1*trade.QUANTITY));          
        }
        if (AgentToExposure.containsKey(trade.TO)){
          AgentToExposure.put(buyer, (int) (AgentToExposure.get(buyer)+trade.QUANTITY));                    
        } else {
          AgentToExposure.put(buyer,(int) trade.QUANTITY);                    
        }
      }
      
      for (Integer agent: AgentToExposure.keySet()){
        if (coin){
          // agent is buying null for negative price if exposure positive (so if they bought contracts they receive money)
          // agent is buying null for positive price if exposure negatieve
          orders.add(new Order(agent, null, (double) (-100 * AgentToExposure.get(agent)) ,1,null));          
        }
      }
      state.setPayments(orders);      
    }
  }
  
  @Override
  public void reset() {
  }

}
