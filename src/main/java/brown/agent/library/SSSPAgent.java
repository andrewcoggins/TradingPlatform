package brown.agent.library;

import java.util.HashMap;
import java.util.Map;

import brown.agent.AbsSimpleSealedBidAgent;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.RegistrationMessage;
import brown.messages.library.ValuationRegistrationMessage;
import brown.setup.library.LemonadeSetup;
import brown.tradeable.library.MultiTradeable;
import brown.value.valuationrepresentation.library.SimpleValuation;

public class SSSPAgent extends AbsSimpleSealedBidAgent {

  private SimpleValuation privateValuation; 
  
  public SSSPAgent(String host, int port)
      throws AgentCreationException {
    super(host, port, new LemonadeSetup());
    // TODO Auto-generated constructor stub
  }

  @Override
  public void onRegistration(RegistrationMessage registration) {
    this.ID = registration.getID();
    System.out.println("Registration received");
    if (registration instanceof ValuationRegistrationMessage) {
      System.out.println("Registration received");
      ValuationRegistrationMessage valReg = (ValuationRegistrationMessage) registration; 
      this.privateValuation = (SimpleValuation) valReg.getValuation();
    }
  }
  
  @Override
  public void onSimpleSealedBid(SimpleAgentChannel simpleChannel) {
    Map<MultiTradeable, Double> initial = new HashMap<MultiTradeable, Double>();
    // TODO Auto-generated method stub
    for (MultiTradeable t: this.privateValuation.vals.keySet()) {
      initial.put(t, privateValuation.vals.get(t).value);
    }
    // this is the SCPP price prediction. Probably not a very good bid. 
    // where to go from here? 
    simpleChannel.bid(this, initial);
  }
  
  @Override
  public void onBankUpdate(BankUpdateMessage bankUpdate) {
    // TODO Auto-generated method stub
    System.out.println("bank update");
    System.out.println(bankUpdate.toString());
  }

  @Override
  public void onMarketUpdate(GameReportMessage marketUpdate) {
    // TODO Auto-generated method stub
    System.out.println("market update");
    //System.out.println(gameReport.toString());
  }

  public static void main(String[] args) throws AgentCreationException {
    //new SSSPAgent("Kerrys-MacBook-Pro-2.local", 2121);
    //new SSSPAgent("localhost", 2121);
    new SSSPAgent("localhost", 2121);
    
    while(true){}
  }
  
}