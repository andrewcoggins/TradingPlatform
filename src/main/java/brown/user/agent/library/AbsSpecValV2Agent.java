package brown.user.agent.library;

import java.util.HashMap;
import java.util.Map;

import org.spectrumauctions.sats.core.model.mrvm.MRVMBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMLicense;
import org.spectrumauctions.sats.core.model.mrvm.MRVMWorld;
import org.spectrumauctions.sats.core.model.mrvm.MultiRegionModel;
import org.spectrumauctions.sats.core.util.random.JavaUtilRNGSupplier;
import org.spectrumauctions.sats.core.util.random.RNGSupplier;

import brown.mechanism.channel.library.OpenOutcryChannel;
import brown.mechanism.messages.library.GameReportMessage;
import brown.mechanism.messages.library.PrivateInformationMessage;
import brown.mechanism.messages.library.SpecValWrapperMessage;
import brown.system.setup.ISetup;
import brown.user.agent.ISpecValV2Agent;

/**
 * agent that bids in canadian spectrum auctions will extend this class.
 * @author kerry
 *
 */
public abstract class AbsSpecValV2Agent  extends AbsAgent implements ISpecValV2Agent{
  protected MRVMBidder valuation;
  public final Double valueScale = 1E-6;
  protected MultiRegionModel model;
  protected Map<Integer, MRVMLicense> idToLicense;  
  
  public AbsSpecValV2Agent(String host, int port, ISetup gameSetup, String name)
       {
    super(host, port, gameSetup, name);
    this.valuation = null;
    this.model = new MultiRegionModel();
    this.idToLicense = new HashMap<Integer, MRVMLicense>();
  }
  
  public AbsSpecValV2Agent(String host, int port, ISetup gameSetup)
       {
    super(host, port, gameSetup);
    this.valuation = null;
    this.model = new MultiRegionModel();
    this.idToLicense = new HashMap<Integer, MRVMLicense>();    
  }

  @Override
  public abstract void onClockMarket(OpenOutcryChannel channel);

  @Override
  public abstract void onGameReport(GameReportMessage gameReport);

  @Override
  public void onPrivateInformation(PrivateInformationMessage privateInfo) {
    if (privateInfo instanceof SpecValWrapperMessage){    
      SpecValWrapperMessage svMessage = (SpecValWrapperMessage) privateInfo;
      this.model.setNumberOfNationalBidders(svMessage.getnBidders());        
      this.model.setNumberOfRegionalBidders(0);        
      this.model.setNumberOfLocalBidders(0);       
      long seed = svMessage.getSeed();
      RNGSupplier rngSupplier = new JavaUtilRNGSupplier(seed);
      MRVMWorld world = this.model.createWorld(rngSupplier);
      this.valuation = this.model.createPopulation(world, rngSupplier).get(svMessage.getIndex());      
      for (MRVMLicense l : world.getLicenses()){
        this.idToLicense.put((int) l.getId(), l);
      }      
    }
  }
}
