package brown.value.distribution.library; 

import java.util.Set;

import ch.uzh.ifi.ce.mweiss.specval.model.UnsupportedBiddingLanguageException;
import brown.tradeable.ITradeable;
import brown.value.distribution.IValuationDistribution;
import brown.value.generator.library.SpecValGenerator;
import brown.value.valuation.IValuation;

public class BundleValuationDistribution implements IValuationDistribution {

  private Set<ITradeable> goods; 
  private SpecValGenerator generator;
  
  
  public BundleValuationDistribution(int numBidders, int numSamples, int meanSampleSize,
      double stdSampleSize, Set<ITradeable> goods) {
    this.generator = new SpecValGenerator(numBidders, numSamples, 
        meanSampleSize, stdSampleSize, 1.0);
    this.goods = goods;
  }
  
  @Override
  public IValuation sample() {
    try {
      generator.makeValuations();
      
    } catch (UnsupportedBiddingLanguageException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // TODO Auto-generated method stub
    return null;
  }
  
}