package brown.value.distribution.library;

import brown.value.distribution.IValuationDistribution;
import brown.value.generator.IValuationGenerator;
import brown.value.valuable.library.Value;
import brown.value.valuation.IValuation;
import brown.value.valuation.library.DMValuation;

public class ComplementaryValuationDistribution implements IValuationDistribution{

  private IValuationGenerator baseGenerator; 
  private IValuationGenerator DeltaGenerator; 
  private double minBase; 
  private double maxBase; 
  private double minDelta; 
  private double maxDelta; 
  
  /**
   * @param generator
   * generator for the base tradeable value
   * @param DeltaGenerator
   * generator for delta value.
   */
  public ComplementaryValuationDistribution(IValuationGenerator generator, IValuationGenerator DeltaGenerator) {
    this.baseGenerator = generator; 
    this.DeltaGenerator = DeltaGenerator; 
    this.minBase = 0.0; 
    this.maxBase = 1.0; 
    this.minDelta = 1.0; 
    this.maxDelta = 2.0;
  }
  
  /**
   * 
   * @param generator
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
  public ComplementaryValuationDistribution(IValuationGenerator generator, IValuationGenerator DeltaGenerator, 
      Double minBase, Double maxBase, 
      Double minDelta, Double maxDelta) {
    this.baseGenerator = generator; 
    this.minBase = minBase; 
    this.maxBase = maxBase; 
    this.minDelta = minDelta; 
    this.maxDelta = maxDelta;
  }
  
  @Override
  public IValuation sample() {
    Value tentativeBase = new Value(-999);
    Value tentativeDelta = new Value(-999);
    while (tentativeBase.value < minBase || tentativeBase.value > maxBase) {
      tentativeBase = baseGenerator.makeValuation();
    }
    while(tentativeDelta.value < minDelta || tentativeDelta.value > maxDelta) {
      tentativeDelta = DeltaGenerator.makeValuation();
    }
    return new DMValuation(tentativeBase.value, tentativeDelta.value);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((DeltaGenerator == null) ? 0 : DeltaGenerator.hashCode());
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
    ComplementaryValuationDistribution other =
        (ComplementaryValuationDistribution) obj;
    if (DeltaGenerator == null) {
      if (other.DeltaGenerator != null)
        return false;
    } else if (!DeltaGenerator.equals(other.DeltaGenerator))
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

  @Override
  public String toString() {
    return "ComplementaryValuationDistribution [baseGenerator=" + baseGenerator
        + ", DeltaGenerator=" + DeltaGenerator + ", minBase=" + minBase
        + ", maxBase=" + maxBase + ", minDelta=" + minDelta + ", maxDelta="
        + maxDelta + "]";
  }
  
}
