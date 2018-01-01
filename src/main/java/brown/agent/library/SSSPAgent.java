package brown.agent.library;

import java.util.HashMap;
import java.util.Map;

import brown.agent.AbsSimpleSealedAgent;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.BidRequestMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.NegotiateRequestMessage;
import brown.messages.library.RegistrationMessage;
import brown.messages.library.ValuationRegistrationMessage;
import brown.setup.library.SimpleSetup;
import brown.tradeable.library.Tradeable;
import brown.value.valuationrepresentation.library.SimpleValuation;

public class SSSPAgent extends AbsSimpleSealedAgent {

  private SimpleValuation privateValuation; 
  
  public SSSPAgent(String host, int port)
      throws AgentCreationException {
    super(host, port, new SimpleSetup());
    // TODO Auto-generated constructor stub
  }
  
  @Override
  public void onSimpleSealed(SimpleAgentChannel simpleChannel) {
    Map<Tradeable, Double> initial = new HashMap<Tradeable, Double>();
    // TODO Auto-generated method stub
    for (Tradeable t: this.privateValuation.vals.keySet()) {
      initial.put(t, privateValuation.vals.get(t).value);
    }
    simpleChannel.bid(this, initial);
  }
  
  @Override
  public void onMarketUpdate(GameReportMessage marketUpdate) {
    // TODO Auto-generated method stub
    System.out.println("market update");
  }

  @Override
  public void onBankUpdate(BankUpdateMessage bankUpdate) {
    // TODO Auto-generated method stub
    System.out.println("bank update");
    System.out.println(bankUpdate.toString());
  }
  
  @Override
  public void onRegistration(RegistrationMessage reg) {
    this.ID = reg.getID();
    System.out.println("Registration received");
    if (reg instanceof ValuationRegistrationMessage) {
      System.out.println("Registration received");
      ValuationRegistrationMessage valReg = (ValuationRegistrationMessage) reg; 
      this.privateValuation = (SimpleValuation) valReg.getValuation();
    }
  }
  
  public static void main(String[] args) throws AgentCreationException {
    //new SSSPAgent("Kerrys-MacBook-Pro-2.local", 2121);
    //new SSSPAgent("localhost", 2121);
    new SSSPAgent("localhost", 2121);
    
    while(true){}
  }
  
}