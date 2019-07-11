package brown.auction.endowment.distribution.library;

import java.util.List;

import brown.auction.endowment.IEndowment;
import brown.auction.endowment.distribution.IEndowmentDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.platform.item.ICart;

public abstract class AbsEndowmentDistribution
    implements IEndowmentDistribution {

  protected List<IValuationGenerator> generators;
  protected ICart items;

  public AbsEndowmentDistribution(ICart items,
      List<IValuationGenerator> generators) {
    this.generators = generators;
    this.items = items;
  }

  public abstract IEndowment sample();

  @Override
  public String toString() {
    return "AbsEndowmentDistribution [generators=" + generators + ", items="
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
    AbsEndowmentDistribution other = (AbsEndowmentDistribution) obj;
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
