package brown.value.valuation.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.random.ISAACRandom;
import org.apache.commons.math3.random.RandomGenerator;

import brown.tradeable.ITradeable;
import brown.value.generator.AbsValuationGenerator;
import brown.value.generator.library.ValRandGenerator;
import brown.value.valuable.library.Value;
import brown.value.valuation.IDependentValuation;
import brown.value.valuationrepresentation.AbsValuationRepresentation;
import brown.value.valuationrepresentation.library.ComplexValuation;

/**
 * a bundle valuation is more complicated. I gave it the tools implicit in the
 * creation of values without initially creating those values.
 * 
 * @author andrew
 *
 */
//TODO: keep shifting value.
// 
public class BundleValuation implements IDependentValuation {
  
  private Map<ITradeable, Value> valMap;
  private AbsValuationGenerator generator;
  private Set<ITradeable> goods;
  private Boolean monotonic;

  public BundleValuation(Set<ITradeable> goods) {
    this.generator = new ValRandGenerator();
    this.goods = goods;
    this.valMap = new HashMap<ITradeable, Value>();
    this.monotonic = false;
  }

  // if not monotonic, don't set the constraint. There's gonna have to be some
  // uniformity to the way that these
  // functions operate.
  public BundleValuation(AbsValuationGenerator valGenerator, Boolean isMonotonic,
      Set<ITradeable> goods) {
    this.generator = valGenerator;
    this.goods = goods;
    this.valMap = new HashMap<Set<ITradeable>, Value>();
    this.monotonic = isMonotonic;
  }

  @Override
  public ComplexValuation getValuation(Set<ITradeable> goods) {
    Map<Map<Integer, ITradeable>, Value> existingSetsID =
        new HashMap<Map<Integer, ITradeable>, Value>();
    // map to cater to necessary iteration structure for monotonicity
    Map<Map<Integer, ITradeable>, Value> previousSize =
        new HashMap<Map<Integer, ITradeable>, Value>();
    Map<Integer, ITradeable> numberGoods = new HashMap<Integer, ITradeable>();
    int count = 0;
    for (ITradeable good : this.goods) {
      numberGoods.put(count, good);
      count++;
    }
    // give maps starting values
    existingSetsID.put(new HashMap<Integer, ITradeable>(), new Value(0.0));
    previousSize.put(new HashMap<Integer, ITradeable>(), new Value(0.0));
    for (int i = 0; i < numberGoods.size(); i++) {
      // hashmap populated with every subset of size i;
      Map<Map<Integer, ITradeable>, Value> temp =
          new HashMap<Map<Integer, ITradeable>, Value>();
      // for each good in the previous size subset
      for (Map<Integer, ITradeable> e : previousSize.keySet()) {
        // for each good, create a new bundle, as
        for (Integer id : numberGoods.keySet()) {
          Map<Integer, ITradeable> eCopy = new HashMap<Integer, ITradeable>(e);
          if (!e.keySet().contains(id)) {
            eCopy.put(id, numberGoods.get(id));
            if (!temp.containsKey(eCopy)) {
              // Double totalVariance = 0.0;
              // for(int anId : eCopy.keySet()) {
              // for(int secondId : eCopy.keySet()) {
              // totalVariance += varCoVar[anId][secondId];
              // }
              // }
              if (!monotonic) {
                Value bundleValue = new Value(-0.1);
                while (bundleValue.value < 0)
                  bundleValue = generator.makeValuation((Set) eCopy.values());
                temp.put(eCopy, bundleValue);
              } else {
                // apply monotonic constraints.
                Value highestValSubSet = new Value(0.0);
                for (Integer anId : eCopy.keySet()) {
                  Map<Integer, ITradeable> eCopyCopy =
                      new HashMap<Integer, ITradeable>(eCopy);
                  eCopyCopy.remove(anId);
                  if (existingSetsID.containsKey(eCopyCopy)) {
                    if (existingSetsID.get(eCopyCopy).value > highestValSubSet.value) {
                      highestValSubSet = existingSetsID.get(eCopyCopy);
                    }
                  }
                }
                Value sampledValue = new Value(-0.1);
                while (sampledValue.value < highestValSubSet.value) {
                  sampledValue = generator.makeValuation(new HashSet<ITradeable>(eCopy.values()));
                }
                temp.put(eCopy, sampledValue);
              }
            }
          }
        }
      }
      existingSetsID.putAll(temp);
      previousSize = temp;
    }
    for (Map<Integer, ITradeable> idGood : existingSetsID.keySet()) {
      Set<ITradeable> goodsSet = new HashSet<ITradeable>(idGood.values());
      valMap.put(goodsSet, existingSetsID.get(idGood));
    }
    return new ComplexValuation(valMap);
  }

  @Override
  public AbsValuationRepresentation getSomeValuations(Integer numValuations,
      Integer bundleSizeMean, Double bundleSizeStdDev) {
    if (bundleSizeMean > 0 && bundleSizeStdDev > 0) {
      Map<Set<ITradeable>, Value> returnMap = new HashMap<Set<ITradeable>, Value>();
      // populateVarCoVarMatrix(bundle);
      RandomGenerator rng = new ISAACRandom();
      NormalDistribution sizeDist =
          new NormalDistribution(rng, bundleSizeMean, bundleSizeStdDev);
      for (int i = 0; i < numValuations; i++) {
        Boolean reSample = true;
        while (reSample) {
          List<ITradeable> goodList = new ArrayList<ITradeable>(this.goods);
          Map<Integer, ITradeable> goodMap = new HashMap<>();
          int size = -1;
          // repeatedly sample bundle size until a valid size is picked.
          while (size < 1 || size > this.goods.size()) {
            size = (int) sizeDist.sample();
          }
          // list of goods to uniformly sample from
          // sample without replacement goods to add to the bundle size times.
          for (int j = 0; j < size; j++) {
            Integer rand = (int) (Math.random() * goodList.size());
            ITradeable aGood = goodList.get(rand);
            goodMap.put(rand, aGood);
            goodList.remove(aGood);
          }
          if (!valMap.containsKey(goodMap.values())) {
            reSample = false;
            Double variance = 0.0;
            Set<ITradeable> goodSet = new HashSet<ITradeable>(goodMap.values());
            // for (Integer id : goodMap.keySet()) {
            // for (Integer idTwo : goodMap.keySet()) {
            // variance += varCoVar[id][idTwo];
            // }
            // }
            if (!this.monotonic) {
              Value bundleValue = new Value(-0.1);
              while (bundleValue.value < 0)
                bundleValue = generator.makeValuation(goodSet);
              returnMap.put(goodSet, bundleValue);
            }
            // if not monotonic, make sure that the added bundle's value is
            // greater than
            // all subsets and less than all supersets.
            else {
              Value minimumPrice = new Value(0.0);
              Value maximumPrice = new Value(Double.MAX_VALUE);
              for (Set<ITradeable> goods : this.valMap.keySet()) {
                if (goodSet.containsAll(goods)
                    && valMap.get(goods).value > minimumPrice.value) {
                  minimumPrice = valMap.get(goods);
                }
                if (goods.containsAll(goodSet)
                    && valMap.get(goods).value < maximumPrice.value) {
                  maximumPrice = valMap.get(goods);
                }
              }
              Value bundleValue = new Value(-0.1);
              while (bundleValue.value < minimumPrice.value && bundleValue.value > maximumPrice.value) {
                bundleValue = generator.makeValuation(goodSet);
              }
              returnMap.put(goodSet, bundleValue);
            }
          }
        }
      }
      return new ComplexValuation(returnMap);
    } else {
      System.out.println("ERROR: bundle size parameters not positive");
      throw new NotStrictlyPositiveException(bundleSizeMean);
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((generator == null) ? 0 : generator.hashCode());
    result = prime * result + ((goods == null) ? 0 : goods.hashCode());
    result = prime * result + ((monotonic == null) ? 0 : monotonic.hashCode());
    result = prime * result + ((valMap == null) ? 0 : valMap.hashCode());
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
    BundleValuation other = (BundleValuation) obj;
    if (generator == null) {
      if (other.generator != null)
        return false;
    } else if (!generator.equals(other.generator))
      return false;
    if (goods == null) {
      if (other.goods != null)
        return false;
    } else if (!goods.equals(other.goods))
      return false;
    if (monotonic == null) {
      if (other.monotonic != null)
        return false;
    } else if (!monotonic.equals(other.monotonic))
      return false;
    if (valMap == null) {
      if (other.valMap != null)
        return false;
    } else if (!valMap.equals(other.valMap))
      return false;
    return true;
  }

}
