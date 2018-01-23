package brown.value.distribution.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.distribution.IValuationDistribution;
import brown.value.generator.IValuationGenerator;
import brown.value.valuation.IValuation;
import brown.value.valuation.library.AdditiveValuation;

/**
 * Distribution for generating additive valuations.
 * @author andrew
 */
public class AdditiveValuationDistribution implements IValuationDistribution {

  private IValuationGenerator generator; 
  Set<SimpleTradeable> toValue; 
  private Map<SimpleTradeable, Double> values; 
  
  
  /**
   * For kryo
   * DO NOT USE
   */
  public AdditiveValuationDistribution(){
    this.generator = null;
    this.toValue = null;
    this.values = null;
  }
  
  /**
   * @param generator
   * a value generator for producing values of individual tradeables.
   * @param goods
   * all tradeables to be valued. These can be input as any type but will be 
   * valued based on the simple tradeables they contain.
   */
  public AdditiveValuationDistribution(IValuationGenerator generator, Set<ITradeable> goods) {
    this.generator = generator; 
    Set<SimpleTradeable> allGoods = new HashSet<SimpleTradeable>();
    for (ITradeable tradeable : goods) {
      List<SimpleTradeable> atoms = tradeable.flatten();
      allGoods.addAll(atoms);
    }
    this.toValue = allGoods; 
    this.values = new HashMap<SimpleTradeable,Double>();
  }
  
  @Override
  public IValuation sample() {
    for (SimpleTradeable atom : this.toValue){ 
      Double aValue = this.generator.makeValuation(); 
      this.values.put(atom, aValue);
    }
    return new AdditiveValuation(this.values);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((generator == null) ? 0 : generator.hashCode());
    result = prime * result + ((toValue == null) ? 0 : toValue.hashCode());
    result = prime * result + ((values == null) ? 0 : values.hashCode());
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
    AdditiveValuationDistribution other = (AdditiveValuationDistribution) obj;
    if (generator == null) {
      if (other.generator != null)
        return false;
    } else if (!generator.equals(other.generator))
      return false;
    if (toValue == null) {
      if (other.toValue != null)
        return false;
    } else if (!toValue.equals(other.toValue))
      return false;
    if (values == null) {
      if (other.values != null)
        return false;
    } else if (!values.equals(other.values))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "AdditiveValuationDistribution [generator=" + generator
        + ", toValue=" + toValue + ", values=" + values + "]";
  }
  
}
