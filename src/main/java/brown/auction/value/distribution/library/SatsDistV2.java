package brown.auction.value.distribution.library;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.spectrumauctions.sats.core.model.mrvm.MRVMBidder;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.library.SatsGenV2;
import brown.auction.value.valuation.IValuation;
import brown.auction.value.valuation.library.SatsValuation;
import brown.logging.library.Logging;
import brown.mechanism.tradeable.library.ComplexTradeable;

/**
 * Distribution for spectrum auction valuations. 
 * @author kerry
 *
 */
public class SatsDistV2 implements IValuationDistribution {
  SatsGenV2 specValGenerator;
  
  public SatsDistV2(){
    this.specValGenerator = null;
  }
  
  public SatsDistV2(int numberOfBidders) {
    this.specValGenerator = new SatsGenV2(numberOfBidders);  
    }

  public SatsDistV2(int numberGlobalBidders,int numberRegionalBidders,int numberLocalBidders) {
    this.specValGenerator = new SatsGenV2(numberGlobalBidders,numberRegionalBidders,numberLocalBidders);
  }
  
  public void setNumBidders(int numberOfBidders) {
    this.specValGenerator = new SatsGenV2(numberOfBidders);  
    }
  
  public Map<Integer,IValuation> sampleAll(List<Integer> agents){
    Map<Integer,IValuation> toReturn = new HashMap<Integer,IValuation>();
    // These are essentially valuation objects
    List<MRVMBidder> bidders = this.specValGenerator.generateBidders();
    if (agents.size() == bidders.size()){
      Collections.shuffle(agents);
      for (int i = 0; i < agents.size(); i++){
        toReturn.put(agents.get(i), new SatsValuation(bidders.get(i)));
      }
    } else {
      Logging.log("sampleAll in SpecValDist: Created distribution with wrong number of players");
    }
    return toReturn;
  }
  
  public Double sampleBundle(ComplexTradeable t){
    List<MRVMBidder> bidders = this.specValGenerator.generateBidders();
    Collections.shuffle(bidders);
    SatsValuation val = new SatsValuation(bidders.get(0));
    return val.queryValue(t);
  }

  @Override
  public IValuation sample() {
    List<MRVMBidder> bidders = this.specValGenerator.generateBidders();
    Collections.shuffle(bidders);    
    return new SatsValuation(bidders.get(0));
  }  
}

