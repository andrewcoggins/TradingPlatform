package brown.auction.value.distribution.library;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.valuation.ISpecificValuation;
import brown.platform.tradeable.ITradeable;

public abstract class AbsValuationDistribution {

  protected List<IValuationGenerator> generators;
  protected Map<String, List<ITradeable>> tradeableNames;
  
  public AbsValuationDistribution(Map<String, List<ITradeable>> tradeableNames,
      List<IValuationGenerator> generators) {
    this.generators = generators;
    this.tradeableNames = tradeableNames;
  }
  
  public abstract ISpecificValuation sample();

  public List<String> getTradeableNames() {
    return new LinkedList<String>(this.tradeableNames.keySet()); 
  }
  
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
    result = prime * result
        + ((tradeableNames == null) ? 0 : tradeableNames.hashCode());
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
    if (tradeableNames == null) {
      if (other.tradeableNames != null)
        return false;
    } else if (!tradeableNames.equals(other.tradeableNames))
      return false;
    return true;
  }

  
  
}
