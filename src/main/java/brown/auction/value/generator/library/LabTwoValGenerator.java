package brown.auction.value.generator.library;

import brown.auction.value.generator.IValuationGenerator;

/**
 * value generator for lab two: 
 * characterized by F(x) = 2x - x^2 with support on [0, 1]
 * @author andrew
 *
 */
public class LabTwoValGenerator implements IValuationGenerator {

  public LabTwoValGenerator() { 
    
  }
  
  @Override
  public Double makeValuation() {
    double y = Math.random();
    Double value = 1 - Math.sqrt(1 - y);
    return value;
  }
  
}