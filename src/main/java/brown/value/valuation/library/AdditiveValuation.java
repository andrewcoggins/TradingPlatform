package brown.value.valuation.library; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.generator.AbsValuationGenerator;
import brown.value.generator.library.ValRandGenerator;
import brown.value.valuable.library.Value;
import brown.value.valuation.IIndependentValuation;
import brown.value.valuationrepresentation.library.Valuation;

/**
 * A Valuation where the values of each good are independent.
 * @author andrew
 *
 */
public class AdditiveValuation implements IIndependentValuation {

  private Map<ITradeable, Value> valMap;
  
  /**
   * For kryo 
   * DO NOT USE
   */
  public AdditiveValuation() {
      this.valMap = null;
  }
  
  /**
   * default constructor
   * @param goods
   */
  public AdditiveValuation(Set<ITradeable> goods) {
    this.valMap = new HashMap<ITradeable, Value>();
    ValRandGenerator rg = new ValRandGenerator();
    for(ITradeable item : goods) {
      List<SimpleTradeable> atoms = item.flatten();
      for (SimpleTradeable atom : atoms) {
        valMap.put(atom, rg.makeValuation(atom));
      }
    }
  }
  
  /**
   * constructor with an input IValuationGenerator and its associated parameters.
   * @param valGenerator
   * @param goods
   */
  public AdditiveValuation(AbsValuationGenerator valGenerator, Set<ITradeable> goods) {
    this.valMap = new HashMap<ITradeable, Value>();
    for(ITradeable item : goods) {
      List<SimpleTradeable> atoms = item.flatten();
      for (SimpleTradeable atom : atoms) {
        valMap.put(atom, valGenerator.makeValuation(atom));
      }
    }
  }
  
  @Override
  public Value getValuation(ITradeable good) {
    double currentValue = 0.0; 
    List<SimpleTradeable> atoms = good.flatten();
    for(SimpleTradeable atom : atoms) {
      currentValue = currentValue + valMap.get(atom).value;
    }
    return new Value(currentValue);
  }

  @Override
  public Valuation getValuation(Set<ITradeable> goods) {
    Map<ITradeable, Value> values = new HashMap<ITradeable, Value>();
    for (ITradeable good : goods) { 
      values.put(good, this.getValuation(good));
    }
    return new Valuation(values);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((valMap == null) ? 0 : valMap.hashCode());
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
    AdditiveValuation other = (AdditiveValuation) obj;
    if (valMap == null) {
      if (other.valMap != null)
        return false;
    } else if (!valMap.equals(other.valMap))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "AdditiveValuation [valMap=" + valMap + "]";
  }
  
}