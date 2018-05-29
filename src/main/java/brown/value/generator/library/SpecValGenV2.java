package brown.value.generator.library;

import java.util.List;
import java.util.Set;

import org.spectrumauctions.sats.core.model.mrvm.MRVMBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMLicense;
import org.spectrumauctions.sats.core.model.mrvm.MRVMWorld;
import org.spectrumauctions.sats.core.model.mrvm.MultiRegionModel;
import org.spectrumauctions.sats.core.util.random.JavaUtilRNGSupplier;
import org.spectrumauctions.sats.core.util.random.RNGSupplier;

/**
 * Specval valuation generator. 
 * @author kerry
 *
 */
public class SpecValGenV2 {
  public final Double valueScale = 1E-6;
  public final RNGSupplier rng = new JavaUtilRNGSupplier("MY SEED".hashCode());;
  
  public Integer numberOfGlobalBidders;
  public Integer numberOfLocalBidders;
  public Integer numberOfRegionalBidders;
    
  public MultiRegionModel multiRegionModel;
  public MRVMWorld world;
  
  public SpecValGenV2(){
    this.numberOfGlobalBidders = null;
    this.numberOfLocalBidders = null;
    this.numberOfRegionalBidders = null;
    this.multiRegionModel = null;
    this.world = null;
  }
  
  public SpecValGenV2(int numberOfBidders) {
    finishConstructor(numberOfBidders,0,0);  
    }
  
  public SpecValGenV2(int numberGlobalBidders,int numberRegionalBidders,int numberLocalBidders) {
    finishConstructor(numberGlobalBidders,numberRegionalBidders,numberLocalBidders);
  }
  
  private void finishConstructor(int nGlobal, int nRegional, int nLocal) {    
    // Define bidder parameters
    this.numberOfGlobalBidders = nGlobal;
    this.numberOfLocalBidders = nRegional;
    this.numberOfRegionalBidders = nLocal;
        
    // Make World
    this.multiRegionModel = new MultiRegionModel();
    multiRegionModel.setNumberOfNationalBidders(this.numberOfGlobalBidders);
    multiRegionModel.setNumberOfLocalBidders(this.numberOfLocalBidders);
    multiRegionModel.setNumberOfRegionalBidders(this.numberOfRegionalBidders);
    this.world = multiRegionModel.createWorld(rng);
  }  
  
  public Set<MRVMLicense> getLicenses() {
    return this.world.getLicenses();
  }
  
  public List<MRVMBidder> generateBidders() {
    return this.multiRegionModel.createPopulation(this.world);
  }
  
}
