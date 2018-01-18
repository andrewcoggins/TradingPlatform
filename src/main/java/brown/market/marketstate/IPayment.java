package brown.market.marketstate;

import java.util.Map;

/**
 * IPayment is the interface for payment. A payment
 * stores a mapping from agent IDs to a double 
 * representing the cost of each agent's currently
 * allocated bundle.
 * 
 * @author kerry
 */
public interface IPayment {
  
  public Map<Integer, Double> getPayment();
  
}