package abrown.misc;

import java.util.Map;

import brown.tradeable.ITradeable;

/**
 * an IPayment 
 * @author andrew
 */
public interface IPayment {
  
  public Map<ITradeable, Double> getPayment();
  
}