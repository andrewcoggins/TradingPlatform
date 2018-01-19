//package brown.value.valuation.library; 
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import brown.tradeable.ITradeable;
//import brown.tradeable.library.SimpleTradeable;
//import brown.value.generator.IValuationGenerator;
//import brown.value.valuable.library.Value;
//import brown.value.valuation.IIndependentValuation;
//
///**
// * A Valuation where the values of each good are independent.
// * @author andrew
// */
//public class AdditiveValuation implements IIndependentValuation {
//
//  private IValuationGenerator generator; 
//  
//  /**
//   * For kryo 
//   * DO NOT USE
//   */
//  public AdditiveValuation() {
//      this.generator = null; 
//  }
//  
//  
//  /**
//   * constructor with an input IValuationGenerator and its associated parameters.
//   * @param valGenerator
//   * @param goods
//   */
//  public AdditiveValuation(IValuationGenerator valGenerator) {
//    this.generator = valGenerator;
//  }
//  
//  private Value generate(SimpleTradeable good) {
//    return this.generator.makeValuation(good);
//  }
//  
//  @Override
//  public Value makeValuation(ITradeable good) {
//    double currentValue = 0.0; 
//    List<SimpleTradeable> atoms = good.flatten();
//    for(SimpleTradeable atom : atoms) {
//      currentValue = currentValue + this.generate(atom).value;
//    }
//    return new Value(currentValue);
//  }
//
//  @Override
//  public Map<ITradeable, Value> makeValuation(Set<ITradeable> goods) {
//    Map<ITradeable, Value> values = new HashMap<ITradeable, Value>();
//    for (ITradeable good : goods) { 
//      values.put(good, this.makeValuation(good));
//    }
//    return values;
//  }
//}

package brown.value.valuation.library; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.generator.IValuationGenerator;
import brown.value.generator.library.UniformValGenerator;
import brown.value.valuable.library.Value;
import brown.value.valuation.IIndependentValuation;

/**
 * A Valuation where the values of each good are independent.
 * @author andrew
 */
public class AdditiveValuation implements IIndependentValuation {
  
  private final Map<ITradeable, Value> valuation;
  
  /**
   * For kryo 
   * DO NOT USE
   */
  public AdditiveValuation() {
      this.valuation = null;
  }
  
  /**
   * Default constructor
   * @param goods
   */
  public AdditiveValuation(Set<ITradeable> goods) {
    this.valuation = new HashMap<ITradeable, Value>();
    UniformValGenerator rg = new UniformValGenerator();
    for(ITradeable item : goods) {
      List<SimpleTradeable> atoms = item.flatten();
      for (SimpleTradeable atom : atoms) {
        valuation.put(atom, rg.makeValuation(atom));
      }
    }
  }
  
  /**
   * constructor with an input IValuationGenerator and its associated parameters.
   * @param valGenerator
   * @param goods
   */
  public AdditiveValuation(IValuationGenerator valGenerator, Set<ITradeable> goods) { // re-order parameters
    this.valuation = new HashMap<ITradeable, Value>();
    for(ITradeable item : goods) {
      List<SimpleTradeable> atoms = item.flatten();
      for (SimpleTradeable atom : atoms) {
        valuation.put(atom, valGenerator.makeValuation(atom));
      }
    }
  }
  
  @Override
  public Value getValuation(ITradeable good) {
    double currentValue = 0.0; 
    List<SimpleTradeable> atoms = good.flatten();
    for(SimpleTradeable atom : atoms) {
      currentValue = currentValue + valuation.get(atom).value;
    }
    return new Value(currentValue);
  }

  @Override
  public Map<ITradeable, Value> getValuation(Set<ITradeable> goods) {
    Map<ITradeable, Value> values = new HashMap<ITradeable, Value>();
    for (ITradeable good : goods) { 
      values.put(good, this.getValuation(good));
    }
    return values;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((valuation == null) ? 0 : valuation.hashCode());
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
    if (valuation == null) {
      if (other.valuation != null)
        return false;
    } else if (!valuation.equals(other.valuation))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "AdditiveValuation [valMap=" + valuation + "]";
  }
  
}