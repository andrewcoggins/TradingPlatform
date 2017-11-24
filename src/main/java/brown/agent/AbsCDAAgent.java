package brown.agent;

import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.setup.ISetup;

public abstract class AbsCDAAgent extends AbsAgent {

  public AbsCDAAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
    // TODO Auto-generated constructor stub
  }
  
  @Override
  public void onSimpleSealed(SimpleAgentChannel simpleWrapper) {
    //Noop
  }
  
  @Override
  public void onSimpleOpenOutcry(SimpleAgentChannel market) {
    //Noop
  }
  
}