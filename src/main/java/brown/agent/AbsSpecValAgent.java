package brown.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.spectrumauctions.sats.core.model.Bundle;
import org.spectrumauctions.sats.core.model.mrvm.MRVMBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMWorld;
import org.spectrumauctions.sats.core.model.mrvm.MultiRegionModel;
import org.spectrumauctions.sats.core.util.random.JavaUtilRNGSupplier;
import org.spectrumauctions.sats.core.util.random.RNGSupplier;

import brown.channels.library.OpenOutcryChannel;
import brown.channels.library.QueryChannel;
import brown.exceptions.AgentCreationException;
import brown.logging.Logging;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.CombinatorialClockReport;
import brown.messages.library.GameReportMessage;
import brown.messages.library.PrivateInformationMessage;
import brown.messages.library.SpecValValuationMessage;
import brown.messages.library.SpecValValuationReport;
import brown.messages.library.SpecValWrapperMessage;
import brown.setup.ISetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;

/**
 * agent that bids in canadian spectrum auctions will extend this class.
 * @author kerry
 *
 */
public abstract class AbsSpecValAgent extends AbsAgent implements ISpecValAgent {
  protected Map<ComplexTradeable, Double> valuation;

  public AbsSpecValAgent(String host, int port, ISetup gameSetup, String name)
      throws AgentCreationException {
    super(host, port, gameSetup,name);
    this.valuation = new HashMap<ComplexTradeable,Double>();
  }

  
  public AbsSpecValAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
    this.valuation = new HashMap<ComplexTradeable,Double>();
  }

  @Override
  public synchronized void onBankUpdate(BankUpdateMessage update) {
    super.onBankUpdate(update);
  }
  
  @Override
  public abstract void onQueryMarket(QueryChannel channel);
  
  @Override
  public abstract void onClockMarket(OpenOutcryChannel channel);
  
  @Override
  public void onGameReport(GameReportMessage gameReport) {
    if (gameReport instanceof SpecValValuationReport){
      SpecValValuationReport svReport = (SpecValValuationReport) gameReport;
      Map<ComplexTradeable, Double> valuationInfo = svReport.getValuation();
      this.valuation.putAll(valuationInfo);
      Logging.log("Valuation Report received: " + valuationInfo.toString());
    } else if (gameReport instanceof CombinatorialClockReport){
      CombinatorialClockReport ccReport = (CombinatorialClockReport) gameReport;
      Map<ITradeable, Double> mywinnings = new HashMap<ITradeable, Double>();
      for (Entry<ITradeable, Double> entry : ccReport.winnings.entrySet()){
        if (entry.getValue()>0){
          mywinnings.put(entry.getKey(), entry.getValue());
        }        
      }
      Logging.log("CCReport Received: " + mywinnings.toString());
    }
  }

  @Override
  public void onPrivateInformation(PrivateInformationMessage privateInfo) {
    if (privateInfo instanceof SpecValValuationMessage){
      SpecValValuationMessage svMessage = (SpecValValuationMessage) privateInfo;
      this.valuation.putAll(svMessage.getValuation());
      Logging.log("Initial Valuation received: " + svMessage.getValuation().toString());
    } else if (privateInfo instanceof SpecValWrapperMessage){    
      SpecValWrapperMessage svMessage = (SpecValWrapperMessage) privateInfo;
      MultiRegionModel multiRegionModel = new MultiRegionModel();
      multiRegionModel.setNumberOfNationalBidders(svMessage.getnBidders());        
      multiRegionModel.setNumberOfRegionalBidders(0);        
      multiRegionModel.setNumberOfLocalBidders(0);                
      long seed = svMessage.getSeed();
      RNGSupplier rngSupplier = new JavaUtilRNGSupplier(seed);
      MRVMWorld world = multiRegionModel.createWorld(rngSupplier);
      System.out.println("THIS AGENT: " + this.ID + "," + seed);
      List<MRVMBidder> bidders = multiRegionModel.createPopulation(world, rngSupplier);
      for (MRVMBidder bidder : bidders){
        System.out.println("THIS AGENT: " + this.ID + "," + "Bidder value full bundle: " + bidder.calculateValue(new Bundle<>(world.getLicenses())));
        System.out.println("THIS AGENT: " + this.ID + "," + "Bidder alpha: " + bidder.getAlpha());
      }            
    }
  }

}
