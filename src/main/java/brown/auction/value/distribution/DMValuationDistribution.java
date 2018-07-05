package brown.auction.value.distribution;

import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.valuation.DMValuation;
import brown.auction.value.valuation.IValuation;

/**
 * Distribution for generating diminishing marginal valuations.
 * Like complementary distribution, specified by base and discount parameters.
 * @author andrew
 */
public class DMValuationDistribution implements IValuationDistribution {

  private IValuationGenerator baseGenerator; 
  private IValuationGenerator discountGenerator; 
  private double minBase; 
  private double maxBase; 
  private double minDiscount; 
  private double maxDiscount; 
  
  /**
   * @param baseGenerator
   * generator for base tradeable value
   * @param discountGenerator
   * generator for discount value
   */
  public DMValuationDistribution(IValuationGenerator baseGenerator, IValuationGenerator discountGenerator) {
    this.baseGenerator = baseGenerator; 
    this.discountGenerator = discountGenerator; 
    this.minBase = 0.0; 
    this.maxBase = 1.0; 
    this.minDiscount = 0.0; 
    this.maxDiscount = 1.0;
  }
  
  /**
   * @param baseGenerator
   * generator for base tradeable value
   * @param discountGenerator
   * generator for discount value
   * @param minBase
   * minimum base value
   * @param maxBase
   * maximum base value
   * @param minDiscount
   * mimimum discount value
   * @param maxDiscount
   * maximum discount value (suggested < 1) 
   */
  public DMValuationDistribution(IValuationGenerator baseGenerator, IValuationGenerator discountGenerator, 
      Double minBase, Double maxBase, 
      Double minDiscount, Double maxDiscount) {
    this.baseGenerator = baseGenerator; 
    this.discountGenerator = discountGenerator; 
    this.minBase = minBase; 
    this.maxBase = maxBase; 
    this.minDiscount = minDiscount; 
    this.maxDiscount = maxDiscount;
  }
  
  @Override
  public IValuation sample() {
    Double tentativeBase = -999.0;
    Double tentativeDiscount = -999.0;
    while (tentativeBase < minBase || tentativeBase > maxBase) {
      tentativeBase = baseGenerator.makeValuation();
    }
    while(tentativeDiscount < minDiscount || tentativeDiscount > maxDiscount) {
      tentativeDiscount = discountGenerator.makeValuation();
    }
    return new DMValuation(tentativeBase, tentativeDiscount);
  }

  @Override
  public String toString() {
    return "DMValuationDistribution [baseGenerator=" + baseGenerator
        + ", discountGenerator=" + discountGenerator + ", minBase=" + minBase
        + ", maxBase=" + maxBase + ", minDiscount=" + minDiscount
        + ", maxDiscount=" + maxDiscount + "]";
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((baseGenerator == null) ? 0 : baseGenerator.hashCode());
    result = prime * result
        + ((discountGenerator == null) ? 0 : discountGenerator.hashCode());
    long temp;
    temp = Double.doubleToLongBits(maxBase);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(maxDiscount);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(minBase);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(minDiscount);
    result = prime * result + (int) (temp ^ (temp >>> 32));
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
    DMValuationDistribution other = (DMValuationDistribution) obj;
    if (baseGenerator == null) {
      if (other.baseGenerator != null)
        return false;
    } else if (!baseGenerator.equals(other.baseGenerator))
      return false;
    if (discountGenerator == null) {
      if (other.discountGenerator != null)
        return false;
    } else if (!discountGenerator.equals(other.discountGenerator))
      return false;
    if (Double.doubleToLongBits(maxBase) != Double
        .doubleToLongBits(other.maxBase))
      return false;
    if (Double.doubleToLongBits(maxDiscount) != Double
        .doubleToLongBits(other.maxDiscount))
      return false;
    if (Double.doubleToLongBits(minBase) != Double
        .doubleToLongBits(other.minBase))
      return false;
    if (Double.doubleToLongBits(minDiscount) != Double
        .doubleToLongBits(other.minDiscount))
      return false;
    return true;
  }
  
}
