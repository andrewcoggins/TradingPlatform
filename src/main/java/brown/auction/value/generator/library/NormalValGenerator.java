package brown.auction.value.generator.library; 

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.ISAACRandom;
import org.apache.commons.math3.random.RandomGenerator;

import brown.auction.value.generator.IValuationGenerator;

/**
 * generates normally distributed values according to an 
 * input mean and variance.
 * @author acoggins
 *
 */
public class NormalValGenerator implements IValuationGenerator {

  private Double mean; 
  private Double variance; 

  public NormalValGenerator() {
    this.mean = null;
    this.variance = null;
  }
  
  public NormalValGenerator (Double mean, Double variance) {
    this.mean = mean; 
    this.variance = variance; 
  }
 
  @Override
  public Double makeValuation() {
    RandomGenerator rng = new ISAACRandom();
    NormalDistribution normalDist = new NormalDistribution(rng, mean, variance);
    Double actualValue = -1.0;
    while (actualValue < 0)
      actualValue = normalDist.sample();
    return actualValue;
  }
  
}