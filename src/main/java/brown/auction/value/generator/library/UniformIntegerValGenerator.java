package brown.auction.value.generator.library;

import java.util.List;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.math3.random.ISAACRandom;

import brown.auction.value.generator.IValuationGenerator;

public class UniformIntegerValGenerator extends AbsValuationGenerator implements IValuationGenerator {

  private UniformIntegerDistribution distribution; 
  
  public UniformIntegerValGenerator() {
    super(null); 
  }
  
  public UniformIntegerValGenerator (List<Double> params) {
    super(params); 
    this.distribution = new UniformIntegerDistribution(new ISAACRandom(),  (int) Math.round(params.get(0)), (int) Math.round(params.get(1))); 
  }

  @Override
  public Double makeValuation() {
    return (double) this.distribution.sample();
  }

}
