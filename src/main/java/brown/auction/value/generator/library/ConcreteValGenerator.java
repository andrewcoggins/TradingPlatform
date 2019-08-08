package brown.auction.value.generator.library;

import java.util.List;

import brown.auction.value.generator.IValuationGenerator;

public class ConcreteValGenerator implements IValuationGenerator {
  
  private Double value; 
  
  public ConcreteValGenerator() {
    this.value = ((Double) null); 
  }
  
  public ConcreteValGenerator(List<Double> values) {
    this.value = values.get(0); 
  }
  
  @Override
  public Double makeValuation() {
    return value;
  }
  
}
