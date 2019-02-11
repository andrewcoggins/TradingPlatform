package brown.auction.value.distribution.library;

import java.util.List;
import java.util.Map;

import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.valuation.IValuation;
import brown.platform.tradeable.ITradeable;

public abstract class AbsValuationDistribution {

  protected List<IValuationGenerator> generators;
  protected Map<String, List<ITradeable>> tradeableNames;
  
  public AbsValuationDistribution(Map<String, List<ITradeable>> tradeableNames,
      List<IValuationGenerator> generators) {
    this.generators = generators;
    this.tradeableNames = tradeableNames;
  }
  
  public abstract IValuation sample();

  @Override
  public String toString() {
    return "AbsValuationDistribution [generators=" + generators
        + ", tradeableNames=" + tradeableNames + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((generators == null) ? 0 : generators.hashCode());
    result =
        prime * result + ((tradeables == null) ? 0 : tradeables.hashCode());
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
    AbsValuationDistribution other = (AbsValuationDistribution) obj;
    if (generators == null) {
      if (other.generators != null)
        return false;
    } else if (!generators.equals(other.generators))
      return false;
    if (tradeables == null) {
      if (other.tradeables != null)
        return false;
    } else if (!tradeables.equals(other.tradeables))
      return false;
    return true;
  }
  
}
