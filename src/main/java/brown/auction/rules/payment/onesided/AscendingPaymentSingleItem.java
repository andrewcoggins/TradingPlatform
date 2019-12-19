package brown.auction.rules.payment.onesided;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IPaymentRule;
import brown.communication.messages.ITradeMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.library.AccountUpdate;
import brown.platform.item.ICart;


public class AscendingPaymentSingleItem implements IPaymentRule {

  @Override
  public void setOrders(IMarketState state, List<ITradeMessage> messages) {
    
    
    Map<String, Double> reserves = state.getReserves(); 
    
    int timeStep = state.getTicks(); 
    
    List<IAccountUpdate> accountUpdates = new LinkedList<IAccountUpdate>();
    
    double itemPayment = 0.0;
    
    for (String itemName : reserves.keySet()) {
      double basePrice = reserves.get(itemName) / timeStep; 
      itemPayment = basePrice * (timeStep - 1); 
      break; 
    }
    
    Map<Integer, List<ICart>> allocations = state.getAllocation(); 
    
    for (Integer agentID : allocations.keySet()) {
      ICart wonItem = allocations.get(agentID).get(0); 
      accountUpdates.add(new AccountUpdate(agentID, itemPayment, wonItem)); 
      state.setPayments(accountUpdates);
    }
    
  }
}