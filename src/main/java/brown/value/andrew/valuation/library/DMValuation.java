package brown.value.andrew.valuation.library;

import java.util.List;

import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.andrew.valuation.IDMValuation;
import brown.value.valuable.library.Value;

/**
 * a valuation over tradeables with diminishing marginal utility.
 * @author andrew
 *
 */
public class DMValuation implements IDMValuation {

  public final Double baseValue; 
  public final Double discountFactor; 
  
  /**
   * 
   * @param baseValue
   * base value of a single tradeable.
   * @param discountFactor
   * discount factor for marginal values of goods in a bundle.
   */
  public DMValuation(Double baseValue, Double discountFactor) {
    this.baseValue = baseValue; 
    this.discountFactor = discountFactor; 
  }
  
  @Override
  public Value getValuation(ITradeable tradeable) {
    List<SimpleTradeable> allTradeables = tradeable.flatten(); 
    int size = allTradeables.size(); 
    double value = 0.0; 
    for (int i = 0; i < size; i++) {
      value = value + this.baseValue * Math.pow(discountFactor, i);
    }
    return new Value(value);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((baseValue == null) ? 0 : baseValue.hashCode());
    result = prime * result
        + ((discountFactor == null) ? 0 : discountFactor.hashCode());
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
    DMValuation other = (DMValuation) obj;
    if (baseValue == null) {
      if (other.baseValue != null)
        return false;
    } else if (!baseValue.equals(other.baseValue))
      return false;
    if (discountFactor == null) {
      if (other.discountFactor != null)
        return false;
    } else if (!discountFactor.equals(other.discountFactor))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "DMValuation [baseValue=" + baseValue + ", discountFactor="
        + discountFactor + "]";
  }
  
  
  
}