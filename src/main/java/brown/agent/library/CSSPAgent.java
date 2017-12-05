package brown.agent.library;

import brown.agent.AbsSimpleSealedAgent;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdate;
import brown.messages.library.GameReport;
import brown.setup.ISetup;

public class CSSPAgent extends AbsSimpleSealedAgent {

  public CSSPAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
    // TODO Auto-generated constructor stub
  }

  @Override
  public void onMarketUpdate(GameReport marketUpdate) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onBankUpdate(BankUpdate bankUpdate) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onSimpleSealed(SimpleAgentChannel simpleWrapper) {
    // TODO Auto-generated method stub
    
  }
  
}