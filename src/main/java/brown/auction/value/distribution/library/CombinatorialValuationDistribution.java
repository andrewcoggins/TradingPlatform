package brown.auction.value.distribution.library;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.valuation.IValuation;
import brown.auction.value.valuation.library.CombinatorialValuation;

/**
 * Valuation distribution for combinatorial(complementary or supplementary) valuations. 
 * Can be described by a base value and discount parameters.
 * @author june
 *
 */
public class CombinatorialValuationDistribution implements IValuationDistribution{

  private IValuationGenerator baseGenerator; 
  private IValuationGenerator discountGenerator; 
  private double minBase; 
  private double maxBase; 
  private double minDiscount; 
  private double maxDiscount; 
  
  /**
   * @param baseGenerator
   * generator for the base tradeable value
   * @param discountGenerator
   * generator for delta value.
   */
  public CombinatorialValuationDistribution(IValuationGenerator baseGenerator, IValuationGenerator discountGenerator, String type) {
    this.baseGenerator = baseGenerator; 
    this.discountGenerator = discountGenerator; 
    this.minBase = 0.0; 
    this.maxBase = 1.0; 
    if (type == "discount") {
        this.minDiscount = 0.0; 
        this.maxDiscount = 1.0;    	
    }else if (type == "complementary") {
        this.minDiscount = 1.0; 
        this.maxDiscount = 2.0;
    }
  }
  
  /**
   * 
   * @param baseGenerator
   * generator for base tradeable value
   * @param discountGenerator
   * generator for discount value
   * @param minBase
   * minimum base value
   * @param maxBase
   * maximum base value
   * @param minDiscount
   * minimum discount value (suggested  > 1 when it's complementary)
   * @param maxDiscount
   * maximum discount value
   */
  public CombinatorialValuationDistribution(IValuationGenerator baseGenerator, IValuationGenerator discountGenerator, 
      Double minBase, Double maxBase, 
      Double minDiscount, Double maxDiscount) {
    this.baseGenerator = baseGenerator; 
    this.discountGenerator = discountGenerator; 
    this.minBase = minBase; 
    this.maxBase = maxBase; 
    this.minDiscount = minDiscount; 
    this.maxDiscount = maxDiscount;
    throw new Error("Min Delta");
  }
  
  @Override
  public IValuation sample() {
    double tentativeBase = -999.0;
    double tentativeDelta = -999.0;
    while (tentativeBase < minBase || tentativeBase > maxBase) {
      tentativeBase = this.baseGenerator.makeValuation();
    }
    while(tentativeDelta < minDiscount || tentativeDelta > maxDiscount) {
      tentativeDelta = this.discountGenerator.makeValuation();
    }
    return new CombinatorialValuation(tentativeBase, tentativeDelta);
  }

  @Override
  public String toString() {
    return "CombinatorialValuationDistribution [baseGenerator=" + baseGenerator
        + ", discountGenerator=" + discountGenerator + ", minBase=" + minBase
        + ", maxBase=" + maxBase + ", minDiscount=" + minDiscount + ", maxDiscount="
        + maxDiscount + "]";
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((discountGenerator == null) ? 0 : discountGenerator.hashCode());
    result = prime * result
        + ((baseGenerator == null) ? 0 : baseGenerator.hashCode());
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
    CombinatorialValuationDistribution other =
        (CombinatorialValuationDistribution) obj;
    if (discountGenerator == null) {
      if (other.discountGenerator != null)
        return false;
    } else if (!discountGenerator.equals(other.discountGenerator))
      return false;
    if (baseGenerator == null) {
      if (other.baseGenerator != null)
        return false;
    } else if (!baseGenerator.equals(other.baseGenerator))
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
