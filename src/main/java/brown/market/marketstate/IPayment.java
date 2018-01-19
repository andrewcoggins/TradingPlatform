package brown.market.marketstate;

import java.util.Map;

/**
 * IPayment is the interface for Payment
 * A Payment stores a map from Agents to Prices
 * @author kerry
 */
public interface IPayment {
  
  public Map<Integer, Double> getPayment();
  
}