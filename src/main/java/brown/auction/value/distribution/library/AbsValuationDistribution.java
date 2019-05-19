package brown.auction.value.distribution.library;

import java.util.LinkedList;
import java.util.List;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.valuation.ISpecificValuation;
import brown.platform.item.ICart;

public abstract class AbsValuationDistribution
    implements IValuationDistribution {

  protected List<IValuationGenerator> generators;
  protected ICart items;

  public AbsValuationDistribution(ICart items,
      List<IValuationGenerator> generators) {
    this.generators = generators;
    this.items = items;
  }

  public abstract ISpecificValuation sample();

  @Override
  public List<String> getItemNames() {
    List<String> itemNames = new LinkedList<String>();
    this.items.getItems().forEach(item -> itemNames.add(item.getName()));
    return itemNames;
  }

  @Override
  public String toString() {
    return "AbsValuationDistribution [generators=" + generators + ", items="
        + items + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((generators == null) ? 0 : generators.hashCode());
    result = prime * result + ((items == null) ? 0 : items.hashCode());
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
    if (items == null) {
      if (other.items != null)
        return false;
    } else if (!items.equals(other.items))
      return false;
    return true;
  }

}
