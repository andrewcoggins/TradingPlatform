package brown.value.andrew.distribution.library;

import brown.value.andrew.distribution.IValuationDistribution;
import brown.value.andrew.valuation.IValuation;
import brown.value.andrew.valuation.library.DMValuation;
import brown.value.generator.IValuationGenerator;
import brown.value.valuable.library.Value;

public class DMValuationDistribution implements IValuationDistribution {

  private IValuationGenerator baseGenerator; 
  private IValuationGenerator discountGenerator; 
  private double minBase; 
  private double maxBase; 
  private double minDiscount; 
  private double maxDiscount; 
  
  /**
   * @param generator
   * generator for base tradeable value
   * @param discountGenerator
   * generator for discount value
   */
  public DMValuationDistribution(IValuationGenerator generator, IValuationGenerator discountGenerator) {
    this.baseGenerator = generator; 
    this.discountGenerator = discountGenerator; 
    this.minBase = 0.0; 
    this.maxBase = 1.0; 
    this.minDiscount = 0.0; 
    this.maxDiscount = 1.0;
  }
  
  /**
   * @param generator
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
   * maximum discount value
   */
  public DMValuationDistribution(IValuationGenerator generator, IValuationGenerator discountGenerator, 
      Double minBase, Double maxBase, 
      Double minDiscount, Double maxDiscount) {
    this.baseGenerator = generator; 
    this.minBase = minBase; 
    this.maxBase = maxBase; 
    this.minDiscount = minDiscount; 
    this.maxDiscount = maxDiscount;
  }
  
  @Override
  public IValuation sample() {
    Value tentativeBase = new Value(-999);
    Value tentativeDiscount = new Value(-999);
    while (tentativeBase.value < minBase || tentativeBase.value > maxBase) {
      tentativeBase = baseGenerator.makeValuation();
    }
    while(tentativeDiscount.value < minDiscount || tentativeDiscount.value > maxDiscount) {
      tentativeDiscount = discountGenerator.makeValuation();
    }
    return new DMValuation(tentativeBase.value, tentativeDiscount.value);
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

  @Override
  public String toString() {
    return "DMValuationDistribution [baseGenerator=" + baseGenerator
        + ", discountGenerator=" + discountGenerator + ", minBase=" + minBase
        + ", maxBase=" + maxBase + ", minDiscount=" + minDiscount
        + ", maxDiscount=" + maxDiscount + "]";
  }
  
}
