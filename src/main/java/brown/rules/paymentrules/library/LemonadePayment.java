package brown.rules.paymentrules.library; 

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.market.marketstate.ICompleteState;
import brown.market.marketstate.library.MarketState;
import brown.market.marketstate.library.Order;
import brown.market.marketstate.library.Payment;
import brown.rules.paymentrules.IPaymentRule;
import brown.setup.Logging;
import brown.tradeable.ITradeable;
import brown.tradeable.TradeableType;

public class LemonadePayment implements IPaymentRule {

  @Override
  public void setOrders(ICompleteState state) {
    
    MarketState internalState = state.getMarketState();
    Map<Integer,List<ITradeable>> alloc = internalState.getAllocation().getAllocation();
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
    internalState.setPayments(new Payment(orders));
    state.setMarketState(internalState);          
  }

  // Not applicable
  @Override
  public void permitShort(ICompleteState state) {    
  }  
}