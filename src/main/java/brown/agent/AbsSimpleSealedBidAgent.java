package brown.agent;

import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.setup.ISetup;

public abstract class AbsSimpleSealedBidAgent extends AbsAgent {

  public AbsSimpleSealedBidAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }

  /**
   * Provides agent response to sealed-bid auction
   * @param simpleWrapper - simple agent channel
   */
  public abstract void onSimpleSealedBid(SimpleAgentChannel simpleWrapper);

}