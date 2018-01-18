package brown.value.valuation.library; 

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.tradeable.ITradeable;
import brown.tradeable.library.MultiTradeable;
import brown.value.generator.AbsValuationGenerator;
import brown.value.generator.library.ValRandGenerator;
import brown.value.valuable.library.Value;
import brown.value.valuation.IIndependentValuation;
import brown.value.valuationrepresentation.library.SimpleValuation;

/**
 * A Valuation where the values of each good are independent.
 * @author andrew
 *
 */
public class AdditiveValuation implements IIndependentValuation {

  private Map<MultiTradeable, Value> valMap;
  
  /**
   * For kryo do not use
   */
  public AdditiveValuation() {
      this.valMap = null;
  }
  
  /**
   * default constructor
   * @param goods
   */
  public AdditiveValuation(Set<MultiTradeable> goods) {
    this.valMap = new HashMap<MultiTradeable, Value>();
    ValRandGenerator rg = new ValRandGenerator();
    for(MultiTradeable item : goods) {
      valMap.put(item, rg.makeValuation(item));
    }
  }
  
  /**
   * constructor with an input IValuationGenerator and its associated parameters.
   * @param valGenerator
   * @param goods
   */
  public AdditiveValuation(AbsValuationGenerator valGenerator, Set<MultiTradeable> goods) {
    this.valMap = new HashMap<MultiTradeable, Value>();
    for(MultiTradeable item : goods) {
      Value value = valGenerator.makeValuation(item);
      valMap.put(item, value);
    }
  }
  
  @Override
  public Value getValuation(MultiTradeable good) {
    return valMap.get(good);
  }

  @Override
  public SimpleValuation getValuation(Set<MultiTradeable> goods) {
    Map<MultiTradeable, Value> valuation = new HashMap<MultiTradeable, Value>();
    for(MultiTradeable item : goods) {
      valuation.put((MultiTradeable) item, valMap.get(item));
    }
    return new SimpleValuation(valuation); 
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
  
}