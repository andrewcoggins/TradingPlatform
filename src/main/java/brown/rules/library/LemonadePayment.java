package brown.rules.library; 

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.logging.Logging;
import brown.market.marketstate.IMarketState;
import brown.market.marketstate.library.Order;
import brown.rules.IPaymentRule;
import brown.tradeable.ITradeable;
import brown.tradeable.library.TradeableType;

public class LemonadePayment implements IPaymentRule {

  @Override
  public void setOrders(IMarketState state) {
    
    Map<Integer,List<ITradeable>> alloc = state.getAllocation();
    List<Order> orders = new LinkedList<Order>();
    
    for (Integer agent : alloc.keySet()){
      for (ITradeable t: alloc.get(agent)){
         // Lemonade game should only have simple tradeables
        if (t.getType() != TradeableType.Simple){
          Logging.log("[LEMONADE PAYMENT ERROR - not simple tradeables]");
        }
        // 1 glass of lemonade is 1 dollar, so the cost is -1 per glass
        orders.add(new Order(agent,null,-1.0,1.0,t));     
      }      
    }        
    state.setPayments(orders);
  }

  @Override
  public void reset() {
  }  
}