package brown.value.generator.library;

import brown.value.generator.IValuationGenerator;

public class SizeDepValGenerator implements IValuationGenerator {

  private Integer size; 
  private Double valueScale;

  public SizeDepValGenerator(Integer size, Double valueScale) {
    this.size = size; 
    this.valueScale = valueScale; 
  }
  
  /**
   * option with no value scale.
   * @param valFunction
   */
  public SizeDepValGenerator(Integer size) {
    this.size = size; 
    this.valueScale = 1.0; 
  }
  
  @Override
  public Double makeValuation() {
    return this.size * this.valueScale;
  }
  
}