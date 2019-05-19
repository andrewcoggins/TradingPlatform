package brown.auction.value.distribution.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.valuation.ISpecificValuation;
import brown.auction.value.valuation.library.AdditiveValuation;
import brown.platform.item.ICart;
import brown.platform.item.ISingleItem;
import brown.platform.item.library.SingleItem;

/**
 * Distribution for generating additive valuations.
 * 
 * @author andrew
 */
public class AdditiveValuationDistribution extends AbsValuationDistribution
    implements IValuationDistribution {

  private Map<ISingleItem, Double> values;

  /**
   * For kryo DO NOT USE
   */
  public AdditiveValuationDistribution() {
    super(null, null);
    this.values = null;
  }

  /**
   * @param generator a value generator for producing values of individual
   *          tradeables.
   * @param goods the tradeables to be assigned values.
   */
  public AdditiveValuationDistribution(ICart items,
      List<IValuationGenerator> generators) {
    super(items, generators);
    this.values = new HashMap<ISingleItem, Double>();
  }

  @Override
  public ISpecificValuation sample() {

    this.items.getItems()
        .forEach(item -> this.values.put(new SingleItem(item.getName()),
            this.generators.get(0).makeValuation()));
    return new AdditiveValuation(this.values);
  }

}
