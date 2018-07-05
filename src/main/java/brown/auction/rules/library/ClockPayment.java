package brown.auction.rules.library;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IPaymentRule;
import brown.mechanism.tradeable.ComplexTradeable;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.accounting.Order;

public class ClockPayment implements IPaymentRule {

  @Override
  public void setOrders(IMarketState state) {
    List<Order> orders = new LinkedList<Order>();
    boolean over = true;
    Map<ITradeable,List<Integer>> altAlloc = state.getAltAlloc();
    for (ITradeable t : altAlloc.keySet()){
      if (altAlloc.get(t).size() > 1){        
        over = false;
      }
    }
    
    // basically replicate the outerTC
    if (over){
      Map<Integer, List<ITradeable>> alloc = state.getAllocation();
      Map<ITradeable, Double> reserves = state.getReserve();
      for (Entry<Integer, List<ITradeable>> entry: alloc.entrySet()){
        ComplexTradeable good = new ComplexTradeable(0,new HashSet<ITradeable>(entry.getValue()));
        Double price  = 0.;
            for (ITradeable t : entry.getValue()){
              price = price + reserves.get(t);
            }
        orders.add(new Order(entry.getKey(),null, price, 1, good));
      }
    }
    state.setPayments(orders);
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub
    
  }

}
