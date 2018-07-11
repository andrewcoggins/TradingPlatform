package brown.auction.value.distribution.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.valuation.IValuation;
import brown.auction.value.valuation.library.XORValuation;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.ComplexTradeable;

/**
 * Produces xor valuations, or valuations that map values to each possible bundle of tradeables. 
 * not recommended for very large numbers of Tradeables. 
 * experimental
 * @author andrew
 *
 */
public class XORValuationDistribution implements IValuationDistribution {

  private IValuationGenerator generator; 
  private Set<ITradeable> goods; 
  private Map<ComplexTradeable, Double> values; 
  
  public XORValuationDistribution(IValuationGenerator generator, Set<ITradeable> goods) { 
    this.generator = generator; 
    this.goods = goods; 
    this.values = new HashMap<ComplexTradeable, Double>(); 
  }
  
  @Override
  public IValuation sample() {
    // generate one valuation per each bundle of goods.
    // enforce monotonicity. 
    
    Map<Map<Integer, ITradeable>, Double> existingSetsID =
        new HashMap<Map<Integer, ITradeable>, Double>();
    // map to cater to necessary iteration structure for monotonicity
    Map<Map<Integer, ITradeable>, Double> previousSize =
        new HashMap<Map<Integer, ITradeable>,Double>();

    Map<Integer, ITradeable> numberGoods = new HashMap<Integer, ITradeable>();
    int count = 0;
    for (ITradeable good : this.goods) {
      numberGoods.put(count, good);
      count++;
    }
    
    // give maps starting values
    existingSetsID.put(new HashMap<Integer, ITradeable>(), 0.0);
    previousSize.put(new HashMap<Integer, ITradeable>(), 0.0);
    
    for (int i = 0; i < numberGoods.size(); i++) {
      // hashmap populated with every subset of size i;
      Map<Map<Integer, ITradeable>, Double> temp =
          new HashMap<Map<Integer, ITradeable>, Double>();
      // for each good in the previous size subset
      for (Map<Integer, ITradeable> e : previousSize.keySet()) {
        // for each good, create a new bundle, as
        for (Integer id : numberGoods.keySet()) {
          Map<Integer, ITradeable> eCopy = new HashMap<Integer, ITradeable>(e);
          if (!e.keySet().contains(id)) {
            eCopy.put(id, numberGoods.get(id));
            if (!temp.containsKey(eCopy)) {
              // apply monotonic constraints.
              Double highestValSubSet = 0.0;
              for (Integer anId : eCopy.keySet()) {
                Map<Integer, ITradeable> eCopyCopy =
                    new HashMap<Integer, ITradeable>(eCopy);
                eCopyCopy.remove(anId);
                if (existingSetsID.containsKey(eCopyCopy)) {
                  if (existingSetsID.get(eCopyCopy) > highestValSubSet) {
                    highestValSubSet = existingSetsID.get(eCopyCopy);
                  }
                }
              }
              Double sampledValue = -0.1;
              while (sampledValue < highestValSubSet ) {
                sampledValue += 0.1; 
                for (int j = 0; j < eCopy.size(); j++) {
                  sampledValue += generator.makeValuation();
                }
              }
              temp.put(eCopy, sampledValue);
            }
          }
        }
      }
    existingSetsID.putAll(temp);
    previousSize = temp;
  }
  for (Map<Integer, ITradeable> idGood : existingSetsID.keySet()) {
    Set<ITradeable> goodsSet = new HashSet<ITradeable>(idGood.values());
    this.values.put(new ComplexTradeable(0, goodsSet), existingSetsID.get(idGood));
  }  
  
    return new XORValuation(this.values);
  }
  
// for basic testing purposes
//  public static void main(String[] args) { 
//    Set<ITradeable> goodSet = new HashSet<ITradeable>(); 
//    for (int i = 0; i < 4; i++) {
//      goodSet.add(new SimpleTradeable(i)); 
//    }
//    XORValuationDistribution dist = new XORValuationDistribution(new NormalValGenerator(1.0, .1), goodSet); 
//    System.out.println(dist.sample()); 
//    }
  
}