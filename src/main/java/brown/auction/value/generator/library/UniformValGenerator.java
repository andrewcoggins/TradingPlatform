package brown.auction.value.generator.library;

import brown.auction.value.generator.IValuationGenerator;

/**
 * Draws a value from a uniformly distribution.
 * @author andrew
 */
public class UniformValGenerator implements IValuationGenerator {
  
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
  public Double makeValuation() {
    return (Math.random() * this.MAX) + this.MIN;
  }
  
}