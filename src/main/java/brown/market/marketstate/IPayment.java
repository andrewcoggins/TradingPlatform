package brown.market.marketstate;

import java.util.List;

import brown.market.marketstate.library.Order;

/**
 * IPayment is the interface for Payment
 * A Payment stores a map from Agents to Prices
 * @author kerry
 */
public interface IPayment {
  
  public List<Order> getOrders();
  
}