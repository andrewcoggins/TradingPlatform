package brown.agent.library;

import brown.agent.AbsSimpleSealedAgent;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.GameReportMessage;
import brown.setup.ISetup;

/**
 * complex simultaneous second price agent.
 * @author andrew
 *
 */
public class CSSPAgent extends AbsSimpleSealedAgent {

  public CSSPAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
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
  public void onSimpleSealed(SimpleAgentChannel simpleWrapper) {
    // TODO Auto-generated method stub
    
  }
  
}