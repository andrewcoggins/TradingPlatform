package brown.value.generator.library;

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.generator.AbsValuationGenerator;
import brown.value.valuable.library.Value;

/**
 * Generates a value drawn from a uniformly distribution.
 * @author andrew
 */
public class UniformValGenerator extends AbsValuationGenerator {
  
  private final Double MIN; 
  private final Double MAX; 
  
  public UniformValGenerator() {
    this.MIN = 0.0; 
    this.MAX = 1.0;
  }
  
  public UniformValGenerator(double MAX, double MIN) { 
    this.MIN = MIN; 
    this.MAX = MAX; 
  }

  @Override
  public Value makeValuation(ITradeable good) {
    return new Value((Math.random() * this.MAX) + this.MIN);
  }

  // this should go away
  @Override
  public Value makeValuation(Set<ITradeable> goods) {
    Double setMin = this.MIN * goods.size();
    Double setMax = this.MAX * goods.size();
    return new Value((Math.random() * setMax) + setMin);
  }
  
}