package brown.auction.value.config.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.spectrumauctions.sats.core.model.mrvm.MRVMBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMWorld;
import org.spectrumauctions.sats.core.model.mrvm.MultiRegionModel;
import org.spectrumauctions.sats.core.util.random.JavaUtilRNGSupplier;
import org.spectrumauctions.sats.core.util.random.RNGSupplier;

import brown.auction.prevstate.library.PrevStateInfo;
import brown.auction.value.config.IValuationConfig;
import brown.auction.value.valuation.library.ValuationType;
import brown.logging.library.Logging;
import brown.platform.messages.library.PrivateInformationMessage;
import brown.platform.messages.library.SpecValWrapperMessage;

public class SpecValV3Config extends ValConfig implements IValuationConfig {
  Long seed;
  public Map<Integer,MRVMBidder> agentToValue;
  public final Double valueScale = 1E-6;

  
  //problem: number of bidders.
  public SpecValV3Config() {
    super(ValuationType.Spectrum_v2);
    this.seed = new Random().nextLong();       
    this.agentToValue = new HashMap<Integer,MRVMBidder>();
  }
  
  @Override
  public PrevStateInfo generateInfo() {
    return null;
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
    RNGSupplier rng = new JavaUtilRNGSupplier(this.seed);

    MRVMWorld world = model.createWorld(rng);
    List<MRVMBidder> vals = model.createPopulation(world, rng);
    
    if (vals.size() != bidders.size()){
      Logging.log("Error in val config - sizes don't match");
    }
    for (int i = 0; i < bidders.size(); i++){
      this.agentToValue.put(bidders.get(i),vals.get(i));
    }
  }

  @Override
  public Map<Integer, PrivateInformationMessage> generateReport(List<Integer> collection) {
    Map<Integer, PrivateInformationMessage> toReturn = new HashMap<Integer,PrivateInformationMessage>();
    Integer nBidders = collection.size();
    for (Integer agent : collection){
      toReturn.put(agent,new SpecValWrapperMessage(agent,this.seed, collection.indexOf(agent), nBidders));
      }
    return toReturn;  
    }

}
