package brown.agent.library;

import java.util.HashMap;
import java.util.Map;

import brown.agent.AbsSSSPAgent;
import brown.bid.bidbundle.library.AuctionBidBundle;
import brown.channels.agent.library.SSSPChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.RegistrationMessage;
import brown.messages.library.ValuationRegistrationMessage;
import brown.setup.Logging;
import brown.setup.library.LemonadeSetup;
import brown.setup.library.SSSPSetup;
import brown.tradeable.ITradeable;
import brown.value.distribution.library.AdditiveValuationDistribution;
import brown.value.valuation.library.AdditiveValuation;

public class SSSPAgent extends AbsSSSPAgent {

  private AdditiveValuation valuation; 
  private AdditiveValuationDistribution dist;
  
  public SSSPAgent(String host, int port)
      throws AgentCreationException {
    super(host, port, new SSSPSetup());
    // TODO Auto-generated constructor stub
  }

  @Override
  public void onRegistration(RegistrationMessage registration) {
    this.ID = registration.getID();
    if (registration instanceof ValuationRegistrationMessage) {
      Logging.log("[SSSPAgent] Valuation received");
      ValuationRegistrationMessage valReg = (ValuationRegistrationMessage) registration; 
      if (!(valReg.getValuation() instanceof AdditiveValuation)){
        Logging.log("[SSSPAgent] Wrong Valuation Type");
      }
      if (!(valReg.getDistribution() instanceof AdditiveValuationDistribution)){
        Logging.log("[SSSPAgent] Wrong Valuation Distribution");        
      }      
      this.valuation = (AdditiveValuation) valReg.getValuation();
      this.dist = (AdditiveValuationDistribution) valReg.getDistribution();           
    }
  }
  
  @Override
  public void onSSSP(SSSPChannel simpleChannel) {
    Map<ITradeable, Double> initial = new HashMap<ITradeable, Double>();
    // TODO Auto-generated method stub
    for (ITradeable t: this.valuation.) {
      initial.put(t, privateValuation.valuation.get(t).value);
    }
    // this is the SCPP price prediction. Probably not a very good bid. 
    // where to go from here? 
    simpleChannel.bid(this, new AuctionBidBundle(initial));
  }
  
  @Override
  public void onBankUpdate(BankUpdateMessage bankUpdate) {
    System.out.println("bank update");
    System.out.println(bankUpdate.toString());
  }

  @Override
  public void onMarketUpdate(GameReportMessage marketUpdate) {
    System.out.println("market update");
  }

  public static void main(String[] args) throws AgentCreationException {
    new SSSPAgent("localhost", 2121);
    
    while(true){}
  }
}