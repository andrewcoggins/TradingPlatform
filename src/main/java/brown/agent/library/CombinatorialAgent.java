package brown.agent.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import brown.agent.AbsSimpleSealedAgent;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.RegistrationMessage;
import brown.messages.library.ValuationRegistrationMessage;
import brown.setup.ISetup;
import brown.setup.library.SimpleSetup;
import brown.tradeable.library.Tradeable;
import brown.value.valuationrepresentation.library.ComplexValuation;

/**
 * complex simultaneous second price agent.
 * @author andrew
 *
 */
public class CombinatorialAgent extends AbsSimpleSealedAgent {

  private ComplexValuation privateValuation; 
  
  public CombinatorialAgent(String host, int port)
      throws AgentCreationException {
    super(host, port, new SimpleSetup());
    // TODO Auto-generated constructor stub
  }

  @Override
  public void onMarketUpdate(GameReportMessage marketUpdate) {
    // TODO Auto-generated method stub
  }

  @Override
  public void onBankUpdate(BankUpdateMessage bankUpdate) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onRegistration(RegistrationMessage reg) {
    this.ID = reg.getID();
    System.out.println("Registration received");
    if (reg instanceof ValuationRegistrationMessage) {
      System.out.println("Registration received");
      ValuationRegistrationMessage valReg = (ValuationRegistrationMessage) reg; 
      this.privateValuation = (ComplexValuation) valReg.getValuation();
    }
  }
  
  @Override
  public void onSimpleSealed(SimpleAgentChannel simpleWrapper) {
    // TODO Auto-generated method stub
    Map<Set<Tradeable>, Double> bidMap =new HashMap<Set<Tradeable>, Double>();
    simpleWrapper.xorBid(this, bidMap);
    }
  
  public static void main(String[] args) throws AgentCreationException {
    new CombinatorialAgent("localhost", 2121);
    while(true){}
  }
}