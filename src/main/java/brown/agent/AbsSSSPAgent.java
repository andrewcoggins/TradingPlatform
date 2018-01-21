package brown.agent;

import brown.channels.agent.library.SSSPChannel;
import brown.exceptions.AgentCreationException;
import brown.setup.ISetup;

public abstract class AbsSSSPAgent extends AbsAgent {

  public AbsSSSPAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }

  /**
   * Provides agent response to sealed-bid auction
   * @param simpleWrapper - simple agent channel
   */
  public abstract void onSSSP(SSSPChannel channel);

}