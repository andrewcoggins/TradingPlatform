package brown.auction.value.generator.library; 

import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.ISAACRandom;

import brown.auction.value.generator.IValuationGenerator;

/**
 * generates normally distributed values according to an 
 * input mean and variance.
 * @author acoggins
 *
 */
public class NormalValGenerator extends AbsValuationGenerator implements IValuationGenerator {
 
  private NormalDistribution distribution; 

  public NormalValGenerator() {
    super(null); 
    this.distribution = null; 
  }
  
  public NormalValGenerator (List<Double> params) {
    super(params); 
    this.distribution = new NormalDistribution(new ISAACRandom(), params.get(0), params.get(1)); 
  }
  
  @Override
  public Double makeValuation() {
    Double actualValue = -1.0;
    while (actualValue < 0)
      actualValue = this.distribution.sample();
    return actualValue;
  }

}