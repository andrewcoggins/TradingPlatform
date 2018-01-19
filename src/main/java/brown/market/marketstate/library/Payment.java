package brown.market.marketstate.library;

import java.util.LinkedList;
import java.util.List;

import brown.market.marketstate.IPayment;

/**
 * A payment maps ITradeables to prices.
 * @author andrew
 */
public class Payment implements IPayment {
  
  private List<Order> orders; 
 
  public Payment(){
    this.orders = new LinkedList<Order>();
  }
  
  public Payment(List<Order> orders) {
    this.orders = orders; 
  }
  
  @Override
  public List<Order> getOrders() {
    return this.orders;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((orders == null) ? 0 : orders.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Payment && ((Payment) obj).orders.equals(this.orders));
  }

  @Override
  public String toString() {
    return "Payment [" + orders + "]";
  }  
}