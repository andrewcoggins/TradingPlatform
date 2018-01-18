package brown.value.generator.library;

import java.util.Set;
import java.util.function.Function;

import brown.tradeable.ITradeable;
import brown.tradeable.library.MultiTradeable;
import brown.value.generator.AbsValuationGenerator;
import brown.value.valuable.library.Value;

public class SizeDependentGenerator extends AbsValuationGenerator {

  private Function<Integer, Double> valFunction; 
  private Double valueScale;

  public SizeDependentGenerator(Function<Integer, Double> valFunction, Double valueScale) {
    this.valFunction = valFunction; 
    this.valueScale = valueScale; 
  }
  
  /**
   * option with no value scale.
   * @param valFunction
   */
  public SizeDependentGenerator(Function<Integer, Double> valFunction) {
    this.valFunction = valFunction; 
    this.valueScale = 1.0; 
  }
  
  @Override
  public Value makeValuation(ITradeable aGood) {
   return new Value(valFunction.apply(1) * this.valueScale);
  }
  
  @Override
  public Value makeValuation(Set<ITradeable> aGood) {
   return new Value(valFunction.apply(aGood.size()) * this.valueScale);
  } 

}