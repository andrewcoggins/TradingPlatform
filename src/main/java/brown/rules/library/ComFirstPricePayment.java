package brown.rules.library;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.accounting.MarketState;
import brown.market.marketstate.ICompleteState;
import brown.market.marketstate.library.Order;
import brown.rules.IPaymentRule;
import brown.todeprecate.PaymentType;
import brown.tradeable.library.ComplexTradeable;
import brown.tradeable.library.MultiTradeable;

/**
 * naive first price payment rule.
 * @author andrew
 *
 */
public class ComFirstPricePayment implements IPaymentRule {

  @Override
  public void setPayments(ICompleteState state) {
    // TODO Auto-generated method stub
    List<Order> orders = new LinkedList<Order>();
    ComplexBidBundle original = (ComplexBidBundle) state.getbundleReserve();
    Map<Set<MultiTradeable>, Double> originalMap = original.getBids().bids;
    for (Set<MultiTradeable> aSet :originalMap.keySet()) {
     double price = originalMap.get(aSet).PRICE;
     int recipient = originalMap.get(aSet).AGENTID;
     ComplexTradeable comTradeable = new ComplexTradeable(0, aSet);
     orders.add(new Order(recipient, null, price, 1, comTradeable));
    }
   state.setPayments(orders);
  }

  @Override
  public void setPaymentType(ICompleteState state) {
    // TODO Auto-generated method stub
    state.setPaymentType(PaymentType.FirstPrice);
  }

  @Override
  public void setReserve(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void permitShort(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }
  
}