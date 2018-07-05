package brown.auction.value.valuation;

import java.util.List;

import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.SimpleTradeable;

/**
 * a complementary valuation produces a valuation of goods, where 
 * bundles are preferred to individual goods.
 * @author andrew
 *
 */
public class ComplementaryValuation implements IComplementaryValuation {
  
  private final Double baseValue; 
  private final Double delta; 
  
  /**
   * 
   * @param baseValue
   * the base value of an individual good.
   * @param delta
   * the factor by which a bundle value is better than a individual values.
   */
  public ComplementaryValuation(Double baseValue, Double delta) {
    this.baseValue = baseValue; 
    this.delta = delta; 
  }

  @Override
  public Double getValuation(ITradeable tradeable) {
    List<SimpleTradeable> allTradeables = tradeable.flatten(); 
    int size = allTradeables.size(); 
    double value = 0.0; 
    for (int i = 0; i < size; i++) {
      value = value + this.baseValue * Math.pow(this.delta, i);
    }
    return value;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((baseValue == null) ? 0 : baseValue.hashCode());
    result = prime * result + ((delta == null) ? 0 : delta.hashCode());
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
    ComplementaryValuation other = (ComplementaryValuation) obj;
    if (baseValue == null) {
      if (other.baseValue != null)
        return false;
    } else if (!baseValue.equals(other.baseValue))
      return false;
    if (delta == null) {
      if (other.delta != null)
        return false;
    } else if (!delta.equals(other.delta))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "ComplementaryValuation [baseValue=" + baseValue + ", delta=" + delta
        + "]";
  }

  @Override
  public IValuation safeCopy() {
    return new ComplementaryValuation(this.baseValue, this.delta);
  }
  
  
}