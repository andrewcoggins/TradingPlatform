package brown.auction.value.distribution.library;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.valuation.IValuation;
import brown.auction.value.valuation.library.DMValuation;

/**
 * Valuation distriubution for complementary valuations. 
 * Can be described by a base value and a delta value parameter.
 * @author andrew
 *
 */
public class CombinatorialValuationDistribution implements IValuationDistribution{

  private IValuationGenerator baseGenerator; 
  private IValuationGenerator deltaGenerator; 
  private double minBase; 
  private double maxBase; 
  private double minDelta; 
  private double maxDelta; 
  
  /**
   * @param baseGenerator
   * generator for the base tradeable value
   * @param deltaGenerator
   * generator for delta value.
   */
  public CombinatorialValuationDistribution(IValuationGenerator baseGenerator, IValuationGenerator deltaGenerator) {
    this.baseGenerator = baseGenerator; 
    this.deltaGenerator = deltaGenerator; 
    this.minBase = 0.0; 
    this.maxBase = 1.0; 
    this.minDelta = 1.0; 
    this.maxDelta = 2.0;
  }
  
  /**
   * 
   * @param baseGenerator
   * generator for base tradeable value
   * @param DeltaGenerator
   * generator for delta value
   * @param minBase
   * minimum base value
   * @param maxBase
   * maximum base value
   * @param minDelta
   * minimum delta value (suggested  > 1)
   * @param maxDelta
   * maximum delta value
   */
  public CombinatorialValuationDistribution(IValuationGenerator baseGenerator, IValuationGenerator deltaGenerator, 
      Double minBase, Double maxBase, 
      Double minDelta, Double maxDelta) {
    this.baseGenerator = baseGenerator; 
    this.deltaGenerator = deltaGenerator; 
    this.minBase = minBase; 
    this.maxBase = maxBase; 
    this.minDelta = minDelta; 
    this.maxDelta = maxDelta;
    throw new Error("Min Delta");
  }
  
  @Override
  public IValuation sample() {
    double tentativeBase = -999.0;
    double tentativeDelta = -999.0;
    while (tentativeBase < minBase || tentativeBase > maxBase) {
      tentativeBase = this.baseGenerator.makeValuation();
    }
    while(tentativeDelta < minDelta || tentativeDelta > maxDelta) {
      tentativeDelta = this.deltaGenerator.makeValuation();
    }
    return new DMValuation(tentativeBase, tentativeDelta);
  }

  @Override
  public String toString() {
    return "ComplementaryValuationDistribution [baseGenerator=" + baseGenerator
        + ", deltaGenerator=" + deltaGenerator + ", minBase=" + minBase
        + ", maxBase=" + maxBase + ", minDelta=" + minDelta + ", maxDelta="
        + maxDelta + "]";
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((deltaGenerator == null) ? 0 : deltaGenerator.hashCode());
    result = prime * result
        + ((baseGenerator == null) ? 0 : baseGenerator.hashCode());
    long temp;
    temp = Double.doubleToLongBits(maxBase);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(maxDelta);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(minBase);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(minDelta);
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
    if (deltaGenerator == null) {
      if (other.deltaGenerator != null)
        return false;
    } else if (!deltaGenerator.equals(other.deltaGenerator))
      return false;
    if (baseGenerator == null) {
      if (other.baseGenerator != null)
        return false;
    } else if (!baseGenerator.equals(other.baseGenerator))
      return false;
    if (Double.doubleToLongBits(maxBase) != Double
        .doubleToLongBits(other.maxBase))
      return false;
    if (Double.doubleToLongBits(maxDelta) != Double
        .doubleToLongBits(other.maxDelta))
      return false;
    if (Double.doubleToLongBits(minBase) != Double
        .doubleToLongBits(other.minBase))
      return false;
    if (Double.doubleToLongBits(minDelta) != Double
        .doubleToLongBits(other.minDelta))
      return false;
    return true;
  }

}
