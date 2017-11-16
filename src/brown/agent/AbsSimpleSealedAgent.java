package brown.agent;

import brown.channels.agent.library.CDAAgentChannel;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.setup.ISetup;

public abstract class AbsSimpleSealedAgent extends AbsAgent {

  public AbsSimpleSealedAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }
  
  @Override
  public void onContinuousDoubleAuction(CDAAgentChannel market) {
    //Noop
  }
  
  @Override
  public void onSimpleOpenOutcry(SimpleAgentChannel market) {
    //Noop
  }
  
}