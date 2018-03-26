package brown.value.distribution.library; 

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ch.uzh.ifi.ce.mweiss.specval.model.UnsupportedBiddingLanguageException;
import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.distribution.IValuationDistribution;
import brown.value.generator.library.SpecValGenerator;
import brown.value.valuation.IValuation;
import brown.value.valuation.library.XORValuation;

/**
 * produces valuations according to the specval generator.
 * TODO: interface for valuations that are dependent upon one another.
 * @author acoggins
 *
 */
public class SpecValDistribution implements IValuationDistribution {

  private SpecValGenerator generator;
  private Map<String, SimpleTradeable> specValMapping; 
  private int numSamples; 
  private int meanSampleSize; 
  private double stdSampleSize; 
  
  
  public SpecValDistribution(int numSamples, int meanSampleSize,
      double stdSampleSize) {
    this.numSamples = numSamples; 
    this.meanSampleSize = meanSampleSize; 
    this.stdSampleSize = stdSampleSize; 
    // maps specval goods to platform tradeables.
    this.specValMapping = new HashMap<String, SimpleTradeable>(); 
    for(int i = 0; i < 98; i++) {
      specValMapping.put(i + "", new SimpleTradeable(i));
    }
  }
  
  public void giveNumBidders(int numBidders) {
    this.generator = new SpecValGenerator(numBidders, this.numSamples,
        this.meanSampleSize, this.stdSampleSize, 1E-6); 
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
        int i = 0; 
        for(Entry<Set<String>, Double> toAdd : each.entrySet()) {
          Set<ITradeable> value = new HashSet<ITradeable>();
          for (String s : toAdd.getKey()) {
            SimpleTradeable aTradeable = specValMapping.get(s);
            value.add(aTradeable); 
          }
          ComplexTradeable realTradeable = new ComplexTradeable(i ,value);
          agentMap.put(realTradeable, toAdd.getValue());
          i++;
        }
        allValues.add(new XORValuation(agentMap)); 
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
   * samples a valuation from a random distribution. 
   * The specval generator only makes valuations en masse, unfortunately.
   */
  @Override
  public IValuation sample() {
    Set<IValuation> allValues = sampleAll(); 
    List<IValuation> aValuation = new LinkedList<IValuation>(allValues);
    Collections.shuffle(aValuation);
    if (allValues.size() > 0)
      return aValuation.get(0);
    return null; 
    
    
  }
  
}