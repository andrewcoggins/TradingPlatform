package brown.value.valuation.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.random.ISAACRandom;
import org.apache.commons.math3.random.RandomGenerator;

import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.generator.IValuationGenerator;
import brown.value.generator.library.UniformValGenerator;
import brown.value.valuable.library.Value;
import brown.value.valuation.IDependentValuation;
import brown.value.valuationrepresentation.AbsValuationRepresentation;
import brown.value.valuationrepresentation.library.ComplexValuation;

/**
 * a bundle valuation is more complicated. I gave it the tools implicit in the
 * creation of values without initially creating those values.
 * 
 * @author andrew
 */
//TODO: keep shifting value.
// 
public class BundleValuation implements IDependentValuation {
  
  private Map<ITradeable, Value> valMap;
  private IValuationGenerator generator;
  private Set<ITradeable> goods;
  private Boolean monotonic;

  public BundleValuation(Set<ITradeable> goods) {
    this.generator = new UniformValGenerator();
    this.goods = goods;
    this.valMap = new HashMap<ITradeable, Value>();
    this.monotonic = false;
  }

  public BundleValuation(IValuationGenerator valGenerator, Boolean isMonotonic,
      Set<ITradeable> goods) {
    this.generator = valGenerator;
    this.goods = goods;
    this.valMap = new HashMap<ITradeable, Value>();
    this.monotonic = isMonotonic;
  }

  @Override
  public Map<ITradeable, Value> getValuation(Set<ITradeable> goods) {
    Set<SimpleTradeable> simpleTradeablesSet = new HashSet<SimpleTradeable>();
    for (ITradeable good : goods) {
      List<SimpleTradeable> simpleTradeables = good.flatten();
      simpleTradeablesSet.addAll(simpleTradeables);
    }
    Map<Map<Integer, SimpleTradeable>, Value> existingSetsID =
        new HashMap<Map<Integer, SimpleTradeable>, Value>();
    // map to cater to necessary iteration structure for monotonicity
    Map<Map<Integer, SimpleTradeable>, Value> previousSize =
        new HashMap<Map<Integer, SimpleTradeable>, Value>();
    Map<Integer, SimpleTradeable> numberGoods = new HashMap<Integer, SimpleTradeable>();
    int count = 0;
    for (SimpleTradeable simpleGood : simpleTradeablesSet) {
      numberGoods.put(count, simpleGood);
      count++;
    }
    // give maps starting values
    existingSetsID.put(new HashMap<Integer, SimpleTradeable>(), new Value(0.0));
    previousSize.put(new HashMap<Integer, SimpleTradeable>(), new Value(0.0));
    for (int i = 0; i < numberGoods.size(); i++) {
      // hashmap populated with every subset of size i;
      Map<Map<Integer, SimpleTradeable>, Value> temp =
          new HashMap<Map<Integer, SimpleTradeable>, Value>();
      // for each good in the previous size subset
      for (Map<Integer, SimpleTradeable> e : previousSize.keySet()) {
        // for each good, create a new bundle, as
        for (Integer id : numberGoods.keySet()) {
          Map<Integer, SimpleTradeable> eCopy = new HashMap<Integer, SimpleTradeable>(e);
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
                  bundleValue = generator.makeValuation(new ComplexTradeable(0, (Set) eCopy.values()));
                temp.put(eCopy, bundleValue);
              } else {
                // apply monotonic constraints.
                Value highestValSubSet = new Value(0.0);
                for (Integer anId : eCopy.keySet()) {
                  Map<Integer, SimpleTradeable> eCopyCopy =
                      new HashMap<Integer, SimpleTradeable>(eCopy);
                  eCopyCopy.remove(anId);
                  if (existingSetsID.containsKey(eCopyCopy)) {
                    if (existingSetsID.get(eCopyCopy).value > highestValSubSet.value) {
                      highestValSubSet = existingSetsID.get(eCopyCopy);
                    }
                  }
                }
                Value sampledValue = new Value(-0.1);
                while (sampledValue.value < highestValSubSet.value) {
                  sampledValue = generator.makeValuation(new ComplexTradeable(0, (Set) eCopy.values()));
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
    for (Map<Integer, SimpleTradeable> idGood : existingSetsID.keySet()) {
      Set<ITradeable> goodsSet = new HashSet<ITradeable>();
      goodsSet.addAll(idGood.values());
      ITradeable toHash = new ComplexTradeable(0, goodsSet);
      valMap.put(new ComplexTradeable(0, goodsSet), existingSetsID.get(idGood));
    }
    return valMap;
  }

  @Override
  public Map<ITradeable, Value> getSomeValuations (Integer numValuations,
      Integer bundleSizeMean, Double bundleSizeStdDev) {
    if (bundleSizeMean > 0 && bundleSizeStdDev > 0) {
      Map<ITradeable, Value> returnMap = new HashMap<ITradeable, Value>();
      // populateVarCoVarMatrix(bundle);
      RandomGenerator rng = new ISAACRandom();
      NormalDistribution sizeDist =
          new NormalDistribution(rng, bundleSizeMean, bundleSizeStdDev);
      for (int i = 0; i < numValuations; i++) {
        Boolean reSample = true;
        while (reSample) {
          List<ITradeable> goodList = new ArrayList<ITradeable>(this.goods);
          List<SimpleTradeable> simpleGoods = new LinkedList<SimpleTradeable>();
          for (ITradeable tradeable : goodList) {
            List<SimpleTradeable> atoms = tradeable.flatten();
            for (SimpleTradeable atom : atoms) {
              simpleGoods.add(atom);
            }
          }
          Map<Integer, ITradeable> goodMap = new HashMap<>();
          int size = -1;
          // repeatedly sample bundle size until a valid size is picked.
          while (size < 1 || size > this.goods.size()) {
            size = (int) sizeDist.sample();
          }
          // list of goods to uniformly sample from
          // sample without replacement goods to add to the bundle size times.
          for (int j = 0; j < size; j++) {
            Integer rand = (int) (Math.random() * simpleGoods.size());
            ITradeable aGood = simpleGoods.get(rand);
            goodMap.put(rand, aGood);
            simpleGoods.remove(aGood);
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
              ITradeable comTradeable = new ComplexTradeable(0, goodSet);
              while (bundleValue.value < 0) {
                bundleValue = generator.makeValuation(comTradeable);
              }
              returnMap.put(comTradeable, bundleValue);
            }
            // if monotonic, make sure that the added bundle's value is
            // greater than
            // all subsets and less than all supersets.
            else {
              Value minimumPrice = new Value(0.0);
              Value maximumPrice = new Value(Double.MAX_VALUE);
              for (ITradeable good : this.valMap.keySet()) {
                List<SimpleTradeable> goods = good.flatten();
                if (goodSet.containsAll(goods)
                    && valMap.get(good).value > minimumPrice.value) {
                  minimumPrice = valMap.get(good);
                }
                if (goods.containsAll(goodSet)
                    && valMap.get(goods).value < maximumPrice.value) {
                  maximumPrice = valMap.get(goods);
                }
              }
              ITradeable comGood = new ComplexTradeable(0, goodSet);
              Value bundleValue = new Value(-0.1);
              while (bundleValue.value < minimumPrice.value && bundleValue.value > maximumPrice.value) {
                bundleValue = generator.makeValuation(comGood);
              }
              returnMap.put(comGood, bundleValue);
            }
          }
        }
      }
      //return new ComplexValuation(returnMap);
      return returnMap;
    } else {
      System.out.println("ERROR: bundle size parameters not positive");
      throw new NotStrictlyPositiveException(bundleSizeMean);
    }
  }


}
