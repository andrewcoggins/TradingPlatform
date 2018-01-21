package brown.agent;

import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.setup.ISetup;

/**
 * abstract class for open outcry auction games. 
 * All open outcry agents will implement this class.
 * @author andrew
 *
 */
public abstract class AbsOpenOutcryAgent extends AbsAgent {

  public AbsOpenOutcryAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }
  
  /**
   * Provides agent response to open outcry auction
   * @param simpleWrapper - simple agent channel
   */
  public abstract void onSimpleOpenOutcry(SimpleAgentChannel simpleWrapper);

}