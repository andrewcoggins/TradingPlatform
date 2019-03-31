package brown.auction.value.distribution.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.valuation.IValuation;
import brown.auction.value.valuation.library.AdditiveValuation;
import brown.platform.item.ISingleItem;
import brown.platform.item.library.SingleItem;
import brown.platform.tradeable.ITradeable;

/**
 * Distribution for generating additive valuations.
 * @author andrew
 */
public class AdditiveValuationDistribution extends AbsValuationDistribution implements IValuationDistribution {
 
  private Map<ISingleItem, Double> values; 
  
  /**
   * For kryo
   * DO NOT USE
   */
  public AdditiveValuationDistribution() {
    super(null, null); 
    this.values = null; 
  }
  
  /**
   * @param generator
   * a value generator for producing values of individual tradeables.
   * @param goods
   * the tradeables to be assigned values.
   */
  public AdditiveValuationDistribution(Map<String, List<ITradeable>> goods, List<IValuationGenerator> generators) {
    super(goods, generators); 
    this.values = new HashMap<ISingleItem, Double>(); 
  }
  
  @Override
  public IValuation sample() {
    for (String s : this.tradeableNames.keySet()) {
      for (ITradeable atom : this.tradeableNames.get(s)) { 
        ISingleItem item = new SingleItem(atom); 
        Double aValue = this.generators.get(0).makeValuation(); 
        this.values.put(item, aValue);
        break; 
      }
    }
    return new AdditiveValuation(this.values);
  }

}
