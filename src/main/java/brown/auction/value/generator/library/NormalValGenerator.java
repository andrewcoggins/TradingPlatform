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

  
  @Override
  public String toString() {
    return "NormalValGenerator [mean=" + mean + ", variance=" + variance + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((mean == null) ? 0 : mean.hashCode());
    result = prime * result + ((variance == null) ? 0 : variance.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    NormalValGenerator other = (NormalValGenerator) obj;
    if (mean == null) {
      if (other.mean != null)
        return false;
    } else if (!mean.equals(other.mean))
      return false;
    if (variance == null) {
      if (other.variance != null)
        return false;
    } else if (!variance.equals(other.variance))
      return false;
    return true;
  }
  
  
  
}