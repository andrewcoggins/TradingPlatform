package brown.value.distribution.library; 

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ch.uzh.ifi.ce.mweiss.specval.bidlang.xor.XORBid;
import ch.uzh.ifi.ce.mweiss.specval.model.Bidder;
import ch.uzh.ifi.ce.mweiss.specval.model.UnsupportedBiddingLanguageException;
import ch.uzh.ifi.ce.mweiss.specval.model.mrm.MRMLicense;
import ch.uzh.ifi.ce.mweiss.specvalopt.vcg.external.domain.Bids;
import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.distribution.IValuationDistribution;
import brown.value.generator.library.SpecValGenerator;
import brown.value.valuation.IValuation;
import brown.value.valuation.library.BundleValuation;

/**
 * produces valuations according to the specval generator.
 * TODO: interface for valuations that are dependent upon one another.
 * @author acoggins
 *
 */
public class SpecValDistribution implements IValuationDistribution {

  private Set<ITradeable> goods; 
  private SpecValGenerator generator;
  private Map<String, SimpleTradeable> specValMapping; 
  
  public SpecValDistribution(int numBidders, int numSamples, int meanSampleSize,
      double stdSampleSize) {
    this.generator = new SpecValGenerator(numBidders, numSamples, 
        meanSampleSize, stdSampleSize, 1.0);
    // maps specval goods to platform tradeables.
    for(int i = 0; i < 98; i++) {
      specValMapping.put(i + "", new SimpleTradeable(i));
    }
    this.goods = goods;
  }
  
  public Set<IValuation> sampleAll() {
    try {
      generator.makeValuations();
      Map<String, Map<Set<String>, Double>> allBids = generator
          .convertAllBidsToSimpleBids(generator.allBidderValuations);
      Set<IValuation> allValues = new HashSet<IValuation>();
      //each agent.
      for(Entry<String, Map<Set<String>, Double>> entry : allBids.entrySet()) {
        Map<ComplexTradeable, Double> agentMap = new HashMap<ComplexTradeable, Double>();
        Map<Set<String>, Double> each = entry.getValue(); 
        for(Entry<Set<String>, Double> toAdd : each.entrySet()) {
          Set<ITradeable> value = new HashSet<ITradeable>();
          for (String s : toAdd.getKey()) {
            SimpleTradeable aTradeable = specValMapping.get(s);
            value.add(aTradeable); 
          }
          ComplexTradeable realTradeable = new ComplexTradeable(0 ,value);
          agentMap.put(realTradeable, toAdd.getValue());
        }
        allValues.add(new BundleValuation(agentMap));
      }
      return allValues; 
    } catch (UnsupportedBiddingLanguageException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // TODO Auto-generated method stub 
    return null;
  }
  
  /**
   * not useful becuase valuations are dependent.
   */
  @Override
  public IValuation sample() {

    return null;
  }
  
}