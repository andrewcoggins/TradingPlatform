package brown.agent;

import brown.channels.agent.library.CDAAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.setup.ISetup;

public abstract class AbsCDAAgent extends AbsAgent {

  public AbsCDAAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }
  
  /**
   * Provides agent response to CDAs
   * @param cdaWrapper - CDA agent channel
   */
  public abstract void onContinuousDoubleAuction(CDAAgentChannel cdaWrapper);

}