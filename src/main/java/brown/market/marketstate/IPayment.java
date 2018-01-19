package brown.market.marketstate;

import java.util.List;

import brown.market.marketstate.library.Order;

/**
 * IPayment is the interface for payment. A payment just
 * stores a list of orders. 
 * 
 * @author kerry
 */
public interface IPayment {
  
  public List<Order> getOrders();
}