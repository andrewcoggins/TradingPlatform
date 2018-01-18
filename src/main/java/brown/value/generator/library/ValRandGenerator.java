package brown.value.generator.library;

import java.util.Set;

import brown.tradeable.library.MultiTradeable;
import brown.value.generator.AbsValuationGenerator;
import brown.value.valuable.library.Value;


/**
 * generates a random value between zero and one.
 * @author andrew
 *
 */
public class ValRandGenerator extends AbsValuationGenerator {
  
  private Double minVal; 
  private Double maxVal; 
  
  public ValRandGenerator() {
    this.minVal = 0.0; 
    this.maxVal = 1.0;
  }
  
  public ValRandGenerator(double maxVal, double minVal) { 
    this.minVal = minVal; 
    this.maxVal = maxVal; 
  }

  @Override
  public Value makeValuation(MultiTradeable good) {
    return new Value((Math.random() * this.maxVal) + this.minVal);
  }

  @Override
  public Value makeValuation(Set<MultiTradeable> goods) {
    Double setMin = this.minVal * goods.size();
    Double setMax = this.maxVal * goods.size();
    return new Value((Math.random() * setMax) + setMin);
  }
  
  
  
}