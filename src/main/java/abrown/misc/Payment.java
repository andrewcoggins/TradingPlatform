package abrown.misc;

import java.util.Map;

import brown.tradeable.ITradeable;

/**
 * A payment maps ITradeables to prices for those
 * tradeables.
 * @author andrew
 *
 */
public class Payment implements IPayment {
  
  private Map<ITradeable, Double> aPayment; 
 
  public Payment(Map<ITradeable, Double> aPayment) {
    this.aPayment = aPayment; 
  }
  
  public Map<ITradeable, Double> getPayment() {
    return aPayment;
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
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Payment other = (Payment) obj;
    if (aPayment == null) {
      if (other.aPayment != null)
        return false;
    } else if (!aPayment.equals(other.aPayment))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Payment [aPayment=" + aPayment + "]";
  }
  
  
}