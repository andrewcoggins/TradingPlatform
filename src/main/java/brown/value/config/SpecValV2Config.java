package brown.value.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.spectrumauctions.sats.core.bidlang.xor.SizeBasedUniqueRandomXOR;
import org.spectrumauctions.sats.core.bidlang.xor.XORValue;
import org.spectrumauctions.sats.core.model.Bundle;
import org.spectrumauctions.sats.core.model.UnsupportedBiddingLanguageException;
import org.spectrumauctions.sats.core.model.mrvm.MRVMBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMLicense;
import org.spectrumauctions.sats.core.model.mrvm.MRVMWorld;
import org.spectrumauctions.sats.core.model.mrvm.MultiRegionModel;

import brown.logging.Logging;
import brown.market.library.PrevStateInfo;
import brown.market.library.SpecValInfo;
import brown.messages.library.PrivateInformationMessage;
import brown.messages.library.SpecValValuationMessage;
import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.valuation.ValuationType;

public class SpecValV2Config extends ValConfig implements IValuationConfig {
  public Map<Integer,MRVMBidder> agentToValue;
  private Map<Integer, MRVMLicense> idToLicense;
  public final Double valueScale = 1E-6;
  int nBundle;
  int mean;
  int std;
  
  
  //problem: number of bidders.
  public SpecValV2Config(int nBundle, int mean, int std) {
    super(ValuationType.Spectrum);
    this.idToLicense = new HashMap<Integer, MRVMLicense>();
    this.agentToValue = new HashMap<Integer,MRVMBidder>();
    this.nBundle = nBundle;
    this.mean = mean;
    this.std = std;
  }
  
  @Override
  public void initialize(List<Integer> bidders) {
    // Define bidder parameters
    int nGlobal = bidders.size();        
    // Make World
    MultiRegionModel model = new MultiRegionModel();
    model.setNumberOfNationalBidders(nGlobal);
    model.setNumberOfLocalBidders(0);
    model.setNumberOfRegionalBidders(0);
    MRVMWorld world = model.createWorld();
    List<MRVMBidder> vals = model.createPopulation(world);
    Collections.shuffle(bidders);
    if (vals.size() != bidders.size()){
      Logging.log("Error in val config - sizes don't match");
    }
    for (int i = 0; i < bidders.size(); i++){
      this.agentToValue.put(bidders.get(i),vals.get(i));
    }
    for (MRVMLicense l : world.getLicenses()){
      this.idToLicense.put((int) l.getId(), l);
    }
  }

  @Override
  public Map<Integer, PrivateInformationMessage> generateReport(List<Integer> collection) {
    Map<Integer, PrivateInformationMessage> toReturn = new HashMap<Integer,PrivateInformationMessage>();
    for (Integer agent : collection){
      toReturn.put(agent,new SpecValValuationMessage(agent,generateXORS(agent, this.nBundle, this.mean, this.std)));
    }
    return toReturn;
  }

  @Override
  public PrevStateInfo generateInfo() {
    return new SpecValInfo(this.agentToValue, this.idToLicense);
  }
  
  public Map<ComplexTradeable, Double> generateXORS(Integer agent, int nBundles, int mean, int std){
    Map<ComplexTradeable, Double> toReturn = new HashMap<ComplexTradeable, Double>();    
    SizeBasedUniqueRandomXOR<?> valueFunction;
    MRVMBidder valuation = this.agentToValue.get(agent);
    try {
      valueFunction = (SizeBasedUniqueRandomXOR) valuation.getValueFunction(SizeBasedUniqueRandomXOR.class);
      valueFunction.setDistribution(mean, std, nBundles);
      // Do something with the generated bids
      Iterator<? extends XORValue<?>> xorBidIterator = valueFunction.iterator();
      while (xorBidIterator.hasNext()) {
          XORValue bid = xorBidIterator.next();
          Bundle<MRVMLicense> licenses = bid.getLicenses();
          Set<ITradeable> tradeables = new HashSet<ITradeable>();
          for (MRVMLicense license : licenses){
            tradeables.add(new SimpleTradeable((int) license.getId()));
          }        
          // Always just make ID 0?
          toReturn.put(new ComplexTradeable(0,tradeables), this.valueScale * valuation.calculateValue(licenses).doubleValue());
      }
      return toReturn;
    } catch (UnsupportedBiddingLanguageException e) {
      Logging.log("Unsupported Bidding Language Exception");
      return toReturn;
    }
  }

}
