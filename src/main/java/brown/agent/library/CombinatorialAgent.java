package brown.agent.library;

import brown.agent.AbsSSSPAgent;
import brown.channels.agent.library.SSSPChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.RegistrationMessage;
import brown.setup.library.LemonadeSetup;

/**
 * complex simultaneous second price agent.
 * @author andrew
 *
 */
public class CombinatorialAgent extends AbsSSSPAgent {

  
  public CombinatorialAgent(String host, int port)
      throws AgentCreationException {
    super(host, port, new LemonadeSetup());
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

  }

  public static void main(String[] args) throws AgentCreationException {
    new CombinatorialAgent("localhost", 2121);
    
    while(true){}
  }

  @Override
  public void onSSSP(SSSPChannel channel) {
    // TODO Auto-generated method stub
    
  }
  
}