package brown.auction.value.valuation.library;

import java.util.List;
import brown.auction.value.valuation.IValuation;
import brown.platform.tradeable.ITradeable;
import brown.platform.tradeable.library.SimpleTradeable;

/**
 * a combinatorial valuation produces a valuation of goods, where 
 * bundles are preferred to individual goods.
 * can represent complements or substitutes depending 
 * on the value of the discount factor.
 * @author andrew
 */
public class CombinatorialValuation implements IValuation {
  
  private final Double baseValue; 
  private final Double discountFactor; 
  
  /**
   * 
   * @param baseValue
   * the base value of an individual good.
   * @param discountFactor
   * the factor by which a bundle value is better than a individual values.
   */
  public CombinatorialValuation(Double baseValue, Double discountFactor) {
    this.baseValue = baseValue; 
    this.discountFactor = discountFactor; 
  }

  @Override
  public Double getValuation(ITradeable tradeable) {
    List<SimpleTradeable> allTradeables = tradeable.flatten(); 
    int size = allTradeables.size(); 
    double value = 0.0; 
    for (int i = 0; i < size; i++) {
      value = value + this.baseValue * Math.pow(this.discountFactor, i);
    }
    return value;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((baseValue == null) ? 0 : baseValue.hashCode());
    result = prime * result + ((discountFactor == null) ? 0 : discountFactor.hashCode());
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
    CombinatorialValuation other = (CombinatorialValuation) obj;
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
    return "CombinatorialValuation [baseValue=" + baseValue + ", discountFactor=" + discountFactor
        + "]";
  }

}
