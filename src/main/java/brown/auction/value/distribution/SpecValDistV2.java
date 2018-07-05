package brown.auction.value.distribution;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.spectrumauctions.sats.core.model.mrvm.MRVMBidder;

import brown.auction.value.generator.SpecValGenV2;
import brown.auction.value.valuation.IValuation;
import brown.auction.value.valuation.SpecValValuation;
import brown.logging.Logging;
import brown.mechanism.tradeable.ComplexTradeable;

/**
 * Distribution for spectrum auction valuations. 
 * @author kerry
 *
 */
public class SpecValDistV2 implements IValuationDistribution {
  SpecValGenV2 specValGenerator;
  
  public SpecValDistV2(){
    this.specValGenerator = null;
  }
  
  public SpecValDistV2(int numberOfBidders) {
    this.specValGenerator = new SpecValGenV2(numberOfBidders);  
    }

  public SpecValDistV2(int numberGlobalBidders,int numberRegionalBidders,int numberLocalBidders) {
    this.specValGenerator = new SpecValGenV2(numberGlobalBidders,numberRegionalBidders,numberLocalBidders);
  }
  
  public void setNumBidders(int numberOfBidders) {
    this.specValGenerator = new SpecValGenV2(numberOfBidders);  
    }
  
  public Map<Integer,IValuation> sampleAll(List<Integer> agents){
    Map<Integer,IValuation> toReturn = new HashMap<Integer,IValuation>();
    // These are essentially valuation objects
    List<MRVMBidder> bidders = this.specValGenerator.generateBidders();
    if (agents.size() == bidders.size()){
      Collections.shuffle(agents);
      for (int i = 0; i < agents.size(); i++){
        toReturn.put(agents.get(i), new SpecValValuation(bidders.get(i)));
      }
    } else {
      Logging.log("sampleAll in SpecValDist: Created distribution with wrong number of players");
    }
    return toReturn;
  }
  
  public Double sampleBundle(ComplexTradeable t){
    List<MRVMBidder> bidders = this.specValGenerator.generateBidders();
    Collections.shuffle(bidders);
    SpecValValuation val = new SpecValValuation(bidders.get(0));
    return val.queryValue(t);
  }

  @Override
  public IValuation sample() {
    List<MRVMBidder> bidders = this.specValGenerator.generateBidders();
    Collections.shuffle(bidders);    
    return new SpecValValuation(bidders.get(0));
  }  
}
