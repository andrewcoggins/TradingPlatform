package brown.agent;

import brown.channels.agent.library.CDAAgentChannel;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.setup.ISetup;

public abstract class AbsOpenOutcryAgent extends AbsAgent {

  public AbsOpenOutcryAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }
  
  @Override
  public void onSimpleSealed(SimpleAgentChannel simpleWrapper) {
    //Noop
  }
  
  @Override
  public void onContinuousDoubleAuction(CDAAgentChannel market) {
    //Noop
  }
  
}

