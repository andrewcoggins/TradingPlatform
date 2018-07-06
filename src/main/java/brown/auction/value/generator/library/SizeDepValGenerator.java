package brown.auction.value.generator.library;

import brown.auction.value.generator.IValuationGenerator;

/**
 * size dependent-generator generates valuations over a bundle of
 * an input size.
 * Doesn't make a lot of sense with valuations as they are, should
 * probably be deleted.
 * @author acoggins
 *
 */
public class SizeDepValGenerator implements IValuationGenerator {

  private Integer size; 
  private Double valueScale;

  public SizeDepValGenerator(Integer size, Double valueScale) {
    this.size = size; 
    this.valueScale = valueScale; 
  }
  
  /**
   * size dependent generator takes in a size.
   * @param size
   * size of bundle to be valued.
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