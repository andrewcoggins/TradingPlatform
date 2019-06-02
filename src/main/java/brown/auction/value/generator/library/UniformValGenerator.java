package brown.auction.value.generator.library;

import java.util.List;

import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.random.ISAACRandom;

import brown.auction.value.generator.IValuationGenerator;

public class UniformValGenerator extends AbsValuationGenerator implements IValuationGenerator {

  private UniformRealDistribution distribution; 
  
  public UniformValGenerator() {
    super(null); 
  }
  
  public UniformValGenerator (List<Double> params) {
    super(params); 
    this.distribution = new UniformRealDistribution(new ISAACRandom(), params.get(0), params.get(1)); 
  }

  @Override
  public Double makeValuation() {
    return this.distribution.sample();
  }

}
