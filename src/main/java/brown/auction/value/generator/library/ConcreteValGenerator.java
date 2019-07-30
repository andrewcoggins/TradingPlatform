package brown.auction.value.generator.library;

import brown.auction.value.generator.IValuationGenerator;

public class ConcreteValGenerator implements IValuationGenerator {
  
  private double value; 
  
  public ConcreteValGenerator(double value) {
    this.value = value; 
  }
  
  @Override
  public Double makeValuation() {
    return value;
  }

}
