package brown.market.marketstate.library;

import java.util.Map;

import brown.market.marketstate.IPayment;

/**
 * A Payment stores a map from Agents to Prices
 * @author andrew
 */
public class Payment implements IPayment {
  
  private Map<Integer, Double> aPayment; 
 
  public Payment(Map<Integer, Double> aPayment) {
    this.aPayment = aPayment; 
  }
  
  public Map<Integer, Double> getPayment() {
    return aPayment;
  }

  @Override
  public String toString() {
    return "Payment [" + aPayment + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((aPayment == null) ? 0 : aPayment.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Payment && ((Payment) obj).aPayment.equals(this.aPayment));
  }
  
}