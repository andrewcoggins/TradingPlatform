package brown.rules.paymentrules.library; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.market.marketstate.ICompleteState;
import brown.market.marketstate.library.MarketState;
import brown.market.marketstate.library.Payment;
import brown.rules.paymentrules.IPaymentRule;
import brown.tradeable.ITradeable;

public class LemonadePayment implements IPaymentRule {

  @Override
  public void setPayments(ICompleteState state) {
    
    Map<Integer,Double> payment = new HashMap<Integer,Double>();
    MarketState internalState = state.getMarketState();
    Map<Integer,List<ITradeable>> alloc = internalState.getAllocation().getAllocation();
    for (Integer agent : alloc.keySet()){
      // 1 glass of lemonade is 1 dollar, so the cost is -1 per glass
      payment.put(agent,-1.0*alloc.get(agent).size());
    }
    Payment newPayment = new Payment(payment);
    
    internalState.setPayments(newPayment);
    state.setMarketState(internalState);          
  }

  // Not applicable
  @Override
  public void permitShort(ICompleteState state) {    
  }  
}